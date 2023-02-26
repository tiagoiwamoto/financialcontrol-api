package br.com.kamehouse.financialcontrol.core.usecase;

import br.com.kamehouse.financialcontrol.adapter.InvestmentAdapter;
import br.com.kamehouse.financialcontrol.core.domain.InvestmentDomain;
import br.com.kamehouse.financialcontrol.entrypoint.dto.InvestmentDto;
import br.com.kamehouse.financialcontrol.entrypoint.dto.QueryDto;
import br.com.kamehouse.financialcontrol.entrypoint.enumerate.IncomingTypeEnum;
import br.com.kamehouse.financialcontrol.entrypoint.enumerate.InvestmentTypeEnum;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class InvestmentUsecase {

    @Inject InvestmentAdapter investmentAdapter;

    public InvestmentDto create(InvestmentDto investmentDto){
        investmentDto.setCreatedAt(LocalDateTime.now());
        investmentDto.setUpdatedAt(LocalDateTime.now());
        investmentDto.setUuid(UUID.randomUUID());
        var investmentDomain = InvestmentDomain.of().toDomain(investmentDto);
        var response = this.investmentAdapter.save(investmentDomain);
        var savedRecord = InvestmentDomain.of().toDto(response);
        return savedRecord;
    }

    public InvestmentDto update(InvestmentDto investmentDto){
        var oldRecord = this.investmentAdapter.recovery(investmentDto.getUuid());
        var incomingDomain = InvestmentDomain.of().toDomain(investmentDto);
        this.investmentAdapter.update(incomingDomain);
        var savedRecord = InvestmentDomain.of().toDto(incomingDomain);
        return savedRecord;
    }

    public List<InvestmentDto> recoveryRecords(){
        var recordsDomain = this.investmentAdapter.recoveryAll();
        var records = recordsDomain.stream().map(record -> InvestmentDomain.of().toDto(record)).collect(Collectors.toList());
        return records;
    }

    public List<InvestmentDto> recoveryRecords(List<QueryDto> queryDto){
        String query = "";
        int positionOfQuery = 0;
        Map<String, Object> params = new HashMap<>();
        for(QueryDto condition : queryDto){
            if(condition.getField().equalsIgnoreCase("type")){
                params.put(condition.getField(), InvestmentTypeEnum.valueOf(condition.getValue()));
            }else if(condition.getField().equalsIgnoreCase("data")){
                var dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate data = LocalDate.parse(condition.getValue(), dtf);
                params.put(condition.getField(), data);
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
        var recordsDomain = this.investmentAdapter.recoveryOf(params, query);
        var records = recordsDomain.stream().map(record -> InvestmentDomain.of().toDto(record)).collect(Collectors.toList());
        return records;
    }

    public List<InvestmentDto> recoveryRecordsOfMonthAndYear(Integer month, Integer year){
        var response = this.investmentAdapter.recoveryAllOfMonthYear(month, year);
        var records = response.stream().map(record -> InvestmentDomain.of().toDto(record)).collect(Collectors.toList());
        return records;
    }

    public Boolean removeRecord(UUID uuid){
        var record = this.investmentAdapter.recovery(uuid);
        this.investmentAdapter.remove(record);
        return Boolean.TRUE;
    }
}
