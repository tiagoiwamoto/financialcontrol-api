package br.com.kamehouse.financialcontrol.core.usecase;

import br.com.kamehouse.financialcontrol.adapter.ExpenseAdapter;
import br.com.kamehouse.financialcontrol.core.domain.ExpenseDomain;
import br.com.kamehouse.financialcontrol.entrypoint.dto.ExpenseDto;
import br.com.kamehouse.financialcontrol.entrypoint.dto.QueryDto;
import br.com.kamehouse.financialcontrol.entrypoint.enumerate.ExpenseTypeEnum;
import br.com.kamehouse.financialcontrol.entrypoint.enumerate.IncomingTypeEnum;
import br.com.kamehouse.financialcontrol.entrypoint.mapper.ExpenseMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class ExpenseUsecase {

    @Inject ExpenseAdapter expenseAdapter;

    public ExpenseDto create(ExpenseDto expenseDto){
        var expenseDomain = new ExpenseDomain().toCreate(expenseDto);
        expenseDomain.toCreate();
        var response = this.expenseAdapter.saveExpense(expenseDomain);
        var savedRecord = ExpenseMapper.toDto(response);
        return savedRecord;
    }

    public ExpenseDto update(ExpenseDto expenseDto){
        var oldRecord = this.expenseAdapter.recovery(expenseDto.getUuid());
        var expenseDomain = new ExpenseDomain().toUpdate(expenseDto);
        expenseDomain.toUpdate(oldRecord);
        this.expenseAdapter.updateExpense(expenseDomain, expenseDomain.getId());
        var savedRecord = ExpenseMapper.toDto(expenseDomain);
        return savedRecord;
    }

    public List<ExpenseDto> recoveryRecords(){
        var recordsDomain = this.expenseAdapter.recoveryAllExpenses();
        var records = recordsDomain.stream().map(record -> ExpenseMapper.toDto(record)).collect(Collectors.toList());
        return records;
    }

    public List<ExpenseDto> recoveryRecords(List<QueryDto> queryDto){
        String query = "";
        int positionOfQuery = 0;
        Map<String, Object> params = new HashMap<>();
        for(QueryDto condition : queryDto){
            if(condition.getField().equalsIgnoreCase("type")){
                params.put(condition.getField(), ExpenseTypeEnum.valueOf(condition.getValue()));
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
        var recordsDomain = this.expenseAdapter.recoveryExpenseOf(params, query);
        var records = recordsDomain.stream().map(record -> ExpenseMapper.toDto(record)).collect(Collectors.toList());
        return records;
    }

    public Boolean removeRecord(UUID uuid){
        var record = this.expenseAdapter.recovery(uuid);
        this.expenseAdapter.removeExpense(record);
        return Boolean.TRUE;
    }

}
