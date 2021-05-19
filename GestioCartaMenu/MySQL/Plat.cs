using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Data.Common;
using System.Drawing;
using System.IO;
using System.Text;


namespace MySQL
{
    public class Plat
    {
        private int codi;
        private string nom;
        private string descripcio;
        private decimal preu;
        private byte[] image;
        private bool disponible;
        private int categoria;
        private string nomCat;

        public Plat(int codi, string nom, string descripcio, decimal preu, byte[] image, bool disponible, int categoria, string nomCat)
        {
            this.Codi = codi;
            this.Nom = nom;
            this.Descripcio = descripcio;
            this.Preu = preu;
            this.Image = image;
            this.Disponible = disponible;
            this.Categoria = categoria;
            this.NomCat = nomCat;
        }

        public int Codi { get => codi; set => codi = value; }
        public string Nom { get => nom; set => nom = value; }
        public string Descripcio { get => descripcio; set => descripcio = value; }
        public decimal Preu { get => preu; set => preu = value; }
        public byte[] Image { get => image; set => image = value; }
        public bool Disponible { get => disponible; set => disponible = value; }
        public int Categoria { get => categoria; set => categoria = value; }
        public string NomCat { get => nomCat; set => nomCat = value; }

        public static List<Plat> GetPlat()
        {
            try
            {
                using (Conexio context = new Conexio())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();

                        using (var consulta = connexio.CreateCommand())
                        {
                            consulta.CommandText = @"select p.*,c.nom as nomCat
                                                    from plat p join categoria c on p.categoria=c.codi";

                            DbDataReader reader = consulta.ExecuteReader();

                            List<Plat> plats = new List<Plat>();
                            while (reader.Read())
                            {
                                int codi = reader.GetInt32(reader.GetOrdinal("codi"));
                                string nom = reader.GetString(reader.GetOrdinal("nom"));
                                string desc = reader.GetString(reader.GetOrdinal("DESCRIPCIO_MD"));
                                decimal preu = reader.GetDecimal(reader.GetOrdinal("preu"));
                                int tamany = GetTamanyFoto(codi);
                                byte[] byteArray = new byte[tamany];
                                reader.GetBytes(reader.GetOrdinal("FOTO"),0, byteArray,0,tamany);
                                bool disponible = reader.GetBoolean(reader.GetOrdinal("DISPONIBLE"));
                                int cat = reader.GetInt32(reader.GetOrdinal("CATEGORIA"));
                                string nomCat = reader.GetString(reader.GetOrdinal("nomCat"));
                                Plat plat = new Plat(codi,nom,desc,preu, byteArray, disponible,cat,nomCat);
                                plats.Add(plat);
                            }
                            return plats;
                        }
                    }
                }
            }
            catch (Exception)
            {

            }
            return null;
        }

        public static List<Plat> GetPlatPerCategoria(int catt)
        {
            try
            {
                using (Conexio context = new Conexio())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();

                        using (var consulta = connexio.CreateCommand())
                        {
                            consulta.CommandText = @"select p.*,c.nom as nomCat
                                                    from plat p join categoria c on p.categoria=c.codi
                                                    where p.categoria=@catt";
                            BDUtils.crearParametre(consulta, "catt", System.Data.DbType.Int32, catt);

                            DbDataReader reader = consulta.ExecuteReader();

                            List<Plat> plats = new List<Plat>();
                            while (reader.Read())
                            {
                                int codi = reader.GetInt32(reader.GetOrdinal("codi"));
                                string nom = reader.GetString(reader.GetOrdinal("nom"));
                                string desc = reader.GetString(reader.GetOrdinal("DESCRIPCIO_MD"));
                                decimal preu = reader.GetDecimal(reader.GetOrdinal("preu"));
                                int tamany = GetTamanyFoto(codi);
                                byte[] byteArray = new byte[tamany];
                                reader.GetBytes(reader.GetOrdinal("FOTO"), 0, byteArray, 0, tamany);
                                bool disponible = reader.GetBoolean(reader.GetOrdinal("DISPONIBLE"));
                                int cat = reader.GetInt32(reader.GetOrdinal("CATEGORIA"));
                                string nomCat = reader.GetString(reader.GetOrdinal("nomCat"));
                                Plat plat = new Plat(codi, nom, desc, preu, byteArray, disponible, cat, nomCat);
                                plats.Add(plat);
                            }
                            return plats;
                        }
                    }
                }
            }
            catch (Exception)
            {

            }
            return null;
        }

        public static List<Plat> GetPlatPerNom(string filtre)
        {
            try
            {
                using (Conexio context = new Conexio())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();

                        using (var consulta = connexio.CreateCommand())
                        {
                            consulta.CommandText = @"select p.*,c.nom as nomCat
                                                    from plat p join categoria c on p.categoria=c.codi
                                                    where upper(p.nom) like @filtre";
                            BDUtils.crearParametre(consulta, "filtre", System.Data.DbType.String, "%"+filtre+"%");

                            DbDataReader reader = consulta.ExecuteReader();

                            List<Plat> plats = new List<Plat>();
                            while (reader.Read())
                            {
                                int codi = reader.GetInt32(reader.GetOrdinal("codi"));
                                string nom = reader.GetString(reader.GetOrdinal("nom"));
                                string desc = reader.GetString(reader.GetOrdinal("DESCRIPCIO_MD"));
                                decimal preu = reader.GetDecimal(reader.GetOrdinal("preu"));
                                int tamany = GetTamanyFoto(codi);
                                byte[] byteArray = new byte[tamany];
                                reader.GetBytes(reader.GetOrdinal("FOTO"), 0, byteArray, 0, tamany);
                                bool disponible = reader.GetBoolean(reader.GetOrdinal("DISPONIBLE"));
                                int cat = reader.GetInt32(reader.GetOrdinal("CATEGORIA"));
                                string nomCat = reader.GetString(reader.GetOrdinal("nomCat"));
                                Plat plat = new Plat(codi, nom, desc, preu, byteArray, disponible, cat, nomCat);
                                plats.Add(plat);
                            }
                            return plats;
                        }
                    }
                }
            }
            catch (Exception)
            {

            }
            return null;
        }

        public static int GetTamanyFoto(int codi)
        {

            try
            {
            using (Conexio context = new Conexio())
            {
                using (var connexio = context.Database.GetDbConnection())
                {
                    connexio.Open();
                    int tamany = -1;
                    using (var consulta = connexio.CreateCommand())
                    {
                        consulta.CommandText = @"SELECT OCTET_LENGTH(foto) FROM plat
                                                where codi=@codi";
                        BDUtils.crearParametre(consulta, "codi", System.Data.DbType.Int32, codi);

                        var r = consulta.ExecuteScalar();
                        tamany = (Int32)r;
                        return tamany;
                    }
                }
            }
            }
            catch (Exception)
            {

            }
            return -1;
        }

        public static int GetUltimCodi()
        {

            try
            {
                using (Conexio context = new Conexio())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();
                        int tamany = -1;
                        using (var consulta = connexio.CreateCommand())
                        {
                            consulta.CommandText = @"SELECT codi FROM plat
                                                where codi=(select max(codi) from plat)";
                            var r = consulta.ExecuteScalar();
                            tamany = (Int32)r;
                            return tamany;
                        }
                    }
                }
            }
            catch (Exception)
            {

            }
            return -1;
        }

        public static long platAmbComanda(int codi)
        {

            try
            {
                using (Conexio context = new Conexio())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();
                        long tamany = -1;
                        using (var consulta = connexio.CreateCommand())
                        {
                            consulta.CommandText = @"select count(*)
                                                     from plat p join linia_comanda l on p.CODI=l.PLAT
                                                     where p.CODI=@codi";
                            BDUtils.crearParametre(consulta, "codi", System.Data.DbType.Int32, codi);

                            var r = consulta.ExecuteScalar();
                            tamany = (long)r;
                            return tamany;
                        }
                    }
                }
            }
            catch (Exception)
            {

            }
            return -1;
        }

        public static bool DeletePlat(int codi)
        {

            try
            {
                DbTransaction trans = null;

                using (Conexio context = new Conexio())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();

                        using (var consulta = connexio.CreateCommand())
                        {
                            trans = connexio.BeginTransaction();
                            consulta.Transaction = trans;
                            consulta.CommandText = @"delete
                                                    from plat
                                                    where codi=@codi";
                            BDUtils.crearParametre(consulta, "codi", System.Data.DbType.Int32, codi);

                            int filesAfetades = consulta.ExecuteNonQuery();
                            if (filesAfetades != 1)
                            {
                                trans.Rollback();
                            return false;
                            }
                            else
                            {
                                trans.Commit();
                                return true;
                            }
                        }
                    }
                }

            }
            catch (Exception)
            {

            }
            return false;
        }

        public static bool DeleteLiniaEscandall(int codi)
        {
            try
            {
            DbTransaction trans = null;

            using (Conexio context = new Conexio())
            {
                using (var connexio = context.Database.GetDbConnection())
                {
                    connexio.Open();

                    using (var consulta = connexio.CreateCommand())
                    {
                        trans = connexio.BeginTransaction();
                        consulta.Transaction = trans;
                        consulta.CommandText = @"delete
                                                    from linia_escandall
                                                    where plat=@codi";
                        BDUtils.crearParametre(consulta, "codi", System.Data.DbType.Int32, codi);

                        int filesAfetades = consulta.ExecuteNonQuery();
                        trans.Commit();
                        return true;
                    }
                }
            }
            }
            catch (Exception)
            {

            }
            return false;
        }

        public static bool InsertPlat(int codi, string nom, string desc, decimal preu, string foto,bool disponible, int cat)
        {
            try
            {
                DbTransaction trans = null;
                using (Conexio context = new Conexio())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();

                        using (var consulta = connexio.CreateCommand())
                        {
                            trans = connexio.BeginTransaction();
                            consulta.Transaction = trans;
                            consulta.CommandText = $@"INSERT INTO PLAT VALUES
                                                     (@codi,@nom,@desc,@preu,LOAD_FILE(@foto),@disponible,@cat)";
                            BDUtils.crearParametre(consulta, "codi", System.Data.DbType.Int32, codi);
                            BDUtils.crearParametre(consulta, "nom", System.Data.DbType.String, nom);
                            BDUtils.crearParametre(consulta, "desc", System.Data.DbType.String, desc);
                            BDUtils.crearParametre(consulta, "preu", System.Data.DbType.Decimal, preu);
                            BDUtils.crearParametre(consulta, "foto", System.Data.DbType.String, foto);
                            BDUtils.crearParametre(consulta, "disponible", System.Data.DbType.Boolean, disponible);
                            BDUtils.crearParametre(consulta, "cat", System.Data.DbType.Int32, cat);
                            int filesAfetades = consulta.ExecuteNonQuery();
                            if (filesAfetades != 1)
                            {
                                trans.Rollback();

                            }
                            else
                            {
                                trans.Commit();
                                return true;
                            }
                        }
                    }
                }

            }
            catch (Exception)
            {

            }
            return false;
        }
    }
}

