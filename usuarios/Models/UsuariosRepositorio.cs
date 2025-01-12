using Repositorio;
using MongoDB.Driver;
using Usuarios.Modelo;
using System.Collections.Generic;
using System.Linq;

namespace Usuarios.Repositorio
{
    public class RepositorioUsuariosMongoDB : Repositorio<Usuario, string>
    {
        private readonly IMongoCollection<Usuario> usuarios;

        public RepositorioUsuariosMongoDB()
        {
            var client = new MongoClient("mongodb://root:practicas@mongo:27017");
            var database = client.GetDatabase("usuarios");

            usuarios = database.GetCollection<Usuario>("usuarios");
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