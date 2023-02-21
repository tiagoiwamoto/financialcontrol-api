package br.com.kamehouse.financialcontrol.entrypoint.mapper;

import br.com.kamehouse.financialcontrol.core.domain.IncomingDomain;
import br.com.kamehouse.financialcontrol.entrypoint.dto.IncomingDto;
import org.apache.commons.beanutils.BeanUtils;

public class IncommingMapper {

    public static IncomingDomain toDomain(IncomingDto incomingDto){
        try{
            var incomingDomain = new IncomingDomain();
            BeanUtils.copyProperties(incomingDomain, incomingDto);
            return incomingDomain;
        }catch (Exception e){
            throw new RuntimeException(); //TODO: Criar custom exception
        }
    }

    public static IncomingDto toDto(IncomingDomain incomingDomain){
        try{
            var incomingDto = new IncomingDto();
            BeanUtils.copyProperties(incomingDto, incomingDomain);
            return incomingDto;
        }catch (Exception e){
            throw new RuntimeException(); //TODO: Criar custom exception
        }
    }

}
