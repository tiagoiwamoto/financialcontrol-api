package br.com.kamehouse.financialcontrol.core.usecase;

import br.com.kamehouse.financialcontrol.adapter.IncomingAdapter;
import br.com.kamehouse.financialcontrol.core.domain.IncomingDomain;
import br.com.kamehouse.financialcontrol.entrypoint.dto.IncomingDto;
import br.com.kamehouse.financialcontrol.entrypoint.dto.QueryDto;
import br.com.kamehouse.financialcontrol.entrypoint.enumerate.IncomingTypeEnum;
import br.com.kamehouse.financialcontrol.entrypoint.mapper.IncommingMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class IncomingUsecase {

    @Inject IncomingAdapter incomingAdapter;

    public IncomingDto create(IncomingDto incomingDto){
        var incomingDomain = new IncomingDomain().toCreate(incomingDto);
        incomingDomain.toCreate();
        var response = this.incomingAdapter.saveIncoming(incomingDomain);
        var savedRecord = IncommingMapper.toDto(response);
        return savedRecord;
    }

    public IncomingDto update(IncomingDto incomingDto){
        var oldRecord = this.incomingAdapter.recovery(incomingDto.getUuid());
        var incomingDomain = new IncomingDomain().toUpdate(incomingDto);
        incomingDomain.toUpdate(oldRecord);
        this.incomingAdapter.updateIncoming(incomingDomain, incomingDomain.getId());
        var savedRecord = IncommingMapper.toDto(incomingDomain);
        return savedRecord;
    }

    public List<IncomingDto> recoveryRecords(){
        var recordsDomain = this.incomingAdapter.recoveryAllIncoming();
        var records = recordsDomain.stream().map(record -> IncommingMapper.toDto(record)).collect(Collectors.toList());
        return records;
    }

    public List<IncomingDto> recoveryRecords(List<QueryDto> queryDto){
        String query = "";
        int positionOfQuery = 0;
        Map<String, Object> params = new HashMap<>();
        for(QueryDto condition : queryDto){
            if(condition.getField().equalsIgnoreCase("type")){
                params.put(condition.getField(), IncomingTypeEnum.valueOf(condition.getValue()));
            }else{
                params.put(condition.getField(), condition.getValue());
            }

            if(positionOfQuery == 0){
                query = query.concat(String.format(" %s = :%s", condition.getField(), condition.getField()));
            }else{
                query = query.concat(String.format(" and %s = :%s", condition.getField(), condition.getField()));
            }
            positionOfQuery++;

        }
        var recordsDomain = this.incomingAdapter.recoveryIncomingOf(params, query);
        var records = recordsDomain.stream().map(record -> IncommingMapper.toDto(record)).collect(Collectors.toList());
        return records;
    }

    public Boolean removeRecord(UUID uuid){
        var record = this.incomingAdapter.recovery(uuid);
        this.incomingAdapter.removeIncoming(record);
        return Boolean.TRUE;
    }

}
