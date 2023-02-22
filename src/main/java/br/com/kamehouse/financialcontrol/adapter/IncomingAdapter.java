package br.com.kamehouse.financialcontrol.adapter;

import br.com.kamehouse.financialcontrol.core.domain.IncomingDomain;
import br.com.kamehouse.financialcontrol.entrypoint.enumerate.IncomingTypeEnum;
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
public class IncomingAdapter {

    @Transactional
    public IncomingDomain saveIncoming(IncomingDomain incomingDomain){
        log.info("iniciando gravação no banco de dados");
        try{
            incomingDomain.persistAndFlush();
            log.info("incoming gravado com sucesso, {}", incomingDomain);
            return incomingDomain;
        }catch (Exception e){
            log.error("falha ao gravar incoming, {}", e);
            throw new RuntimeException(); //TODO: Criar exception
        }
    }

    @Transactional
    public IncomingDomain updateIncoming(IncomingDomain incomingDomain, Long id){
        log.info("iniciando gravação no banco de dados");
        try{
            IncomingDomain domain = IncomingDomain.findById(id);
            BeanUtils.copyProperties(domain, incomingDomain);

            log.info("incoming gravado com sucesso, {}", domain);
            return domain;
        }catch (Exception e){
            log.error("falha ao gravar incoming, {}", e);
            throw new RuntimeException(); //TODO: Criar exception
        }
    }

    public List<IncomingDomain> recoveryAllIncoming(){
        log.info("iniciando recuperação de incomings no banco de dados");
        try{
            PanacheQuery<IncomingDomain> response = IncomingDomain.findAll(Sort.by("data", Sort.Direction.Descending));
            log.info("lista de incoming recuperada com sucesso, {}", response.list());
            return response.list();
        }catch (Exception e){
            log.error("falha ao recuperar incoming, {}", e);
            throw new RuntimeException(); //TODO: Criar exception
        }
    }

    public List<IncomingDomain> recoveryIncomingOf(Map<String, Object> params, String query){
        log.info("iniciando recuperação de incomings no banco de dados");
        try{
            PanacheQuery<IncomingDomain> response = IncomingDomain.find(query, params);
            log.info("lista de incoming recuperada com sucesso, {}", response.list());
            return response.list();
        }catch (Exception e){
            log.error("falha ao recuperar incoming, {}", e);
            throw new RuntimeException(); //TODO: Criar exception
        }
    }

    public IncomingDomain recovery(UUID uuid){
        log.info("iniciando recuperação de incoming no banco de dados");
        try{
            Map<String, Object> params = new HashMap<>();
            params.put("uuid", uuid);
            PanacheQuery<IncomingDomain> response = IncomingDomain.find("uuid = :uuid", params);
            return response.firstResultOptional().orElseThrow();
        }catch (Exception e){
            throw new RuntimeException(); //TODO: Criar exception
        }
    }

    @Transactional
    public void removeIncoming(IncomingDomain incomingDomain){
        log.info("iniciando remoção de incomings no banco de dados");
        try{
            incomingDomain.delete();
        }catch (Exception e){
            log.error("falha ao remover incoming, {}", e);
            throw new RuntimeException(); //TODO: Criar exception
        }
    }

}
