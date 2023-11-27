package parcial.backend.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import parcial.backend.demo.entities.InvoiceItem;

import java.util.List;

@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItem,Long> {


    List<InvoiceItem> findByInvoiceId(long id);
}
