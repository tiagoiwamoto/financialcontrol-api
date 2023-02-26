package br.com.kamehouse.financialcontrol.adapter;

import br.com.kamehouse.financialcontrol.core.domain.InvestmentDomain;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
@Slf4j
public class InvestmentAdapter {

    @Inject EntityManager entityManager;

    @Transactional
    public InvestmentDomain save(InvestmentDomain record){
        log.info("iniciando gravação no banco de dados");
        try{
            record.persistAndFlush();
            log.info("investment gravado com sucesso, {}", record);
            return record;
        }catch (Exception e){
            log.error("falha ao gravar investment, {}", e);
            throw new RuntimeException(); //TODO: Criar exception
        }
    }

    @Transactional
    public InvestmentDomain update(InvestmentDomain record){
        log.info("iniciando gravação no banco de dados");
        try{
            InvestmentDomain domain = InvestmentDomain.findById(record.getId());
            BeanUtils.copyProperties(domain, record);

            log.info("investment gravado com sucesso, {}", domain);
            return domain;
        }catch (Exception e){
            log.error("falha ao gravar investment, {}", e);
            throw new RuntimeException(); //TODO: Criar exception
        }
    }

    public List<InvestmentDomain> recoveryAll(){
        log.info("iniciando recuperação de investment no banco de dados");
        try{
            PanacheQuery<InvestmentDomain> response = InvestmentDomain.findAll(Sort.by("data", Sort.Direction.Descending));
            log.info("lista de investment recuperada com sucesso, {}", response.list());
            return response.list();
        }catch (Exception e){
            log.error("falha ao recuperar investment, {}", e);
            throw new RuntimeException(); //TODO: Criar exception
        }
    }

    public List<InvestmentDomain> recoveryOf(Map<String, Object> params, String query){
        log.info("iniciando recuperação de investment no banco de dados");
        try{
            PanacheQuery<InvestmentDomain> response = InvestmentDomain.find(query, params);
            log.info("lista de investment recuperada com sucesso, {}", response.list());
            return response.list();
        }catch (Exception e){
            log.error("falha ao recuperar investment, {}", e);
            throw new RuntimeException(); //TODO: Criar exception
        }
    }

    public List<InvestmentDomain> recoveryAllOfMonthYear(Integer month, Integer year){
        log.info("iniciando recuperação de investment no banco de dados");
        try{
            Query query = entityManager.createNativeQuery("select * from tbl_investment where EXTRACT(MONTH FROM data) = :mes AND EXTRACT(YEAR FROM data) = :ano", InvestmentDomain.class);
            query.setParameter("mes", month);
            query.setParameter("ano", year);
            return query.getResultList();

        }catch (Exception e){
            log.error("falha ao recuperar investment, {}", e);
            throw new RuntimeException(); //TODO: Criar exception
        }
    }

    public InvestmentDomain recovery(UUID uuid){
        log.info("iniciando recuperação de investment no banco de dados");
        try{
            Map<String, Object> params = new HashMap<>();
            params.put("uuid", uuid);
            PanacheQuery<InvestmentDomain> response = InvestmentDomain.find("uuid = :uuid", params);
            return response.firstResultOptional().orElseThrow();
        }catch (Exception e){
            throw new RuntimeException(); //TODO: Criar exception
        }
    }

    @Transactional
    public void remove(InvestmentDomain investmentDomain){
        log.info("iniciando remoção de investment no banco de dados");
        try{
            investmentDomain.delete();
        }catch (Exception e){
            log.error("falha ao remover investment, {}", e);
            throw new RuntimeException(); //TODO: Criar exception
        }
    }

}
