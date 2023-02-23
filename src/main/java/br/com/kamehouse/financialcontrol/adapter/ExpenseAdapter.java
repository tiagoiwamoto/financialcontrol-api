package br.com.kamehouse.financialcontrol.adapter;

import br.com.kamehouse.financialcontrol.core.domain.ExpenseDomain;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
@Slf4j
public class ExpenseAdapter {

    @Transactional
    public ExpenseDomain saveExpense(ExpenseDomain expenseDomain){
        log.info("iniciando gravação no banco de dados");
        try{
            expenseDomain.persistAndFlush();
            log.info("incoming gravado com sucesso, {}", expenseDomain);
            return expenseDomain;
        }catch (Exception e){
            log.error("falha ao gravar incoming, {}", e);
            throw new RuntimeException(); //TODO: Criar exception
        }
    }

    @Transactional
    public ExpenseDomain updateExpense(ExpenseDomain expenseDomain, Long id){
        log.info("iniciando gravação no banco de dados");
        try{
            ExpenseDomain domain = ExpenseDomain.findById(id);
            BeanUtils.copyProperties(domain, expenseDomain);

            log.info("expense gravado com sucesso, {}", domain);
            return domain;
        }catch (Exception e){
            log.error("falha ao gravar expense, {}", e);
            throw new RuntimeException(); //TODO: Criar exception
        }
    }

    public List<ExpenseDomain> recoveryAllExpenses(){
        log.info("iniciando recuperação de expenses no banco de dados");
        try{
            PanacheQuery<ExpenseDomain> response = ExpenseDomain.findAll(Sort.by("data", Sort.Direction.Descending));
            log.info("lista de expenses recuperada com sucesso, {}", response.list());
            return response.list();
        }catch (Exception e){
            log.error("falha ao recuperar expense, {}", e);
            throw new RuntimeException(); //TODO: Criar exception
        }
    }

    public List<ExpenseDomain> recoveryExpenseOf(Map<String, Object> params, String query){
        log.info("iniciando recuperação de expense no banco de dados");
        try{
            PanacheQuery<ExpenseDomain> response = ExpenseDomain.find(query, params);
            log.info("lista de expenses recuperada com sucesso, {}", response.list());
            return response.list();
        }catch (Exception e){
            log.error("falha ao recuperar incoming, {}", e);
            throw new RuntimeException(); //TODO: Criar exception
        }
    }

    public ExpenseDomain recovery(UUID uuid){
        log.info("iniciando recuperação de expense no banco de dados");
        try{
            Map<String, Object> params = new HashMap<>();
            params.put("uuid", uuid);
            PanacheQuery<ExpenseDomain> response = ExpenseDomain.find("uuid = :uuid", params);
            return response.firstResultOptional().orElseThrow();
        }catch (Exception e){
            throw new RuntimeException(); //TODO: Criar exception
        }
    }

    @Transactional
    public void removeExpense(ExpenseDomain expenseDomain){
        log.info("iniciando remoção de expense no banco de dados");
        try{
            expenseDomain.delete();
        }catch (Exception e){
            log.error("falha ao remover expense, {}", e);
            throw new RuntimeException(); //TODO: Criar exception
        }
    }

}
