package estaciones.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import estaciones.modelo.Bicicleta;

@Repository
public interface RepositorioBicicletasMongo
	extends RepositorioBicicletas,
	MongoRepository<Bicicleta, String>{

}
