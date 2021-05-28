using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Data.Common;
using System.Text;

namespace MySQL
{
    public class Catagoria
    {
        private int codi;
        private string nom; 
        private string color;

        public Catagoria(int codi, string nom, string color)
        {
            this.codi = codi;
            this.nom = nom;
            this.color = color;
        }

        public int Codi { get => codi; set => codi = value; }
        public string Nom { get => nom; set => nom = value; }
        public string Color { get => color; set => color = value; }

        public static List<Catagoria> GetCatagorias()
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
                            consulta.CommandText = @"select * from categoria";

                            DbDataReader reader = consulta.ExecuteReader();

                            List<Catagoria> catagorias = new List<Catagoria>();
                            while (reader.Read())
                            {

                                int codi = reader.GetInt32(reader.GetOrdinal("codi"));
                                string nom = reader.GetString(reader.GetOrdinal("nom"));
                                string color = reader.GetString(reader.GetOrdinal("color"));
                                Catagoria cat = new Catagoria(codi,nom,color);
                                catagorias.Add(cat);
                            }
                            return catagorias;
                        }
                    }
                }
            }
            catch (Exception)
            {

            }
            return null;
        }
        public override string ToString()
        {
            return Nom;
        }
    }
}
