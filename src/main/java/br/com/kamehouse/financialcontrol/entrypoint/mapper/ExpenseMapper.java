package br.com.kamehouse.financialcontrol.entrypoint.mapper;

import br.com.kamehouse.financialcontrol.core.domain.ExpenseDomain;
import br.com.kamehouse.financialcontrol.entrypoint.dto.ExpenseDto;
import org.apache.commons.beanutils.BeanUtils;

public class ExpenseMapper {

    public static ExpenseDto toDto(ExpenseDomain expenseDomain){
        try{
            var expenseDto = new ExpenseDto();
            BeanUtils.copyProperties(expenseDto, expenseDomain);
            return expenseDto;
        }catch (Exception e){
            throw new RuntimeException(); //TODO: Criar custom exception
        }
    }

}
