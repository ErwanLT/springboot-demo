package fr.eletutour.configuration;

import fr.eletutour.entity.StockRevisionEntity;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        StockRevisionEntity stockRevisionEntity = (StockRevisionEntity) revisionEntity;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            stockRevisionEntity.setUsername(((UserDetails) principal).getUsername());
        } else {
            stockRevisionEntity.setUsername(principal.toString());
        }
    }
}