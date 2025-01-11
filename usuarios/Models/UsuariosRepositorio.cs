using Repositorio;
using MongoDB.Driver;
using Usuarios.Modelo;

namespace Usuarios.Repositorio
{
    public class RepositorioUsuariosMongoDB : Repositorio<Usuario, string>
    {
        private readonly IMongoCollection<Usuario> usuarios;

        public RepositorioUsuariosMongoDB()
        {
            var client = new MongoClient("mongodb+srv://veelut10:ToLuSoTcSa1321093896@cluster0.jnphq.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0");
            var database = client.GetDatabase("Cluster0");

            usuarios = database.GetCollection<Usuario>("usuarios.net");
        }

        public string Add(Usuario entity)
        {
            usuarios.InsertOne(entity);

            return entity.Id;
        }

        public void Update(Usuario entity)
        {
            usuarios.ReplaceOne(usuario => usuario.Id == entity.Id, entity);
        }

        public void Delete(Usuario entity)
        {
            usuarios.DeleteOne(usuario => usuario.Id == entity.Id);
        }

        public List<Usuario> GetAll()
        {
            return usuarios.Find(_ => true).ToList();
        }

        public Usuario GetById(string id)
        {
            return usuarios
                .Find(usuario => usuario.Id == id)
                .FirstOrDefault();
        }

        public List<string> GetIds()
        {
            var todas =  usuarios.Find(_ => true).ToList();

            return todas.Select(usuario => usuario.Id).ToList();

        }
    }
}