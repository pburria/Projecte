using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Data.Common;
using System.Text;

namespace MySQL
{
    public class Comanda
    {
        private int codi;
        private DateTime data;
        private int taula;
        private int cambrer;
        private string nomCambrer;
        private string cognomCambrer;

        public Comanda(int codi, DateTime data, int taula, int cambrer, string nomCambrer, string cognomCambrer)
        {
            this.Codi = codi;
            this.Data = data;
            this.Taula = taula;
            this.Cambrer = cambrer;
            this.NomCambrer = nomCambrer;
            this.CognomCambrer = cognomCambrer;
        }

        public int Codi { get => codi; set => codi = value; }
        public DateTime Data { get => data; set => data = value; }
        public int Taula { get => taula; set => taula = value; }
        public int Cambrer { get => cambrer; set => cambrer = value; }
        public string NomCambrer { get => nomCambrer; set => nomCambrer = value; }
        public string CognomCambrer { get => cognomCambrer; set => cognomCambrer = value; }

        public static List<Comanda> getComandesNoFinalitzades()
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
                            consulta.CommandText = @"select c.CODI as codii,c.DATE,c.CAMBRER,c.TAULA,ca.nom,ca.COGNOM1
                                                     from comanda c join cambrer ca on c.CAMBRER=ca.CODI
                                                     where c.codi IN (select comanda
                                                                   from linia_comanda 
                                                                   where acabat=false)";

                            DbDataReader reader = consulta.ExecuteReader();

                            List<Comanda> comandas = new List<Comanda>();
                            while (reader.Read())
                            {
                                int codi = reader.GetInt32(reader.GetOrdinal("codii"));
                                DateTime data= reader.GetDateTime(reader.GetOrdinal("date"));
                                int taula = reader.GetInt32(reader.GetOrdinal("TAULA"));
                                int cambrer = reader.GetInt32(reader.GetOrdinal("CAMBRER"));
                                string nomCambrer = reader.GetString(reader.GetOrdinal("nom"));
                                string cognomCambrer = reader.GetString(reader.GetOrdinal("COGNOM1"));
                                Comanda comanda = new Comanda(codi, data, taula, cambrer, nomCambrer, cognomCambrer);
                                comandas.Add(comanda);
                            }
                            return comandas;
                        }
                    }
                }
            }
            catch (Exception)
            {

            }
            return null;
        }

        public override bool Equals(object obj)
        {
            return obj is Comanda comanda &&
                   data == comanda.data &&
                   Data == comanda.Data;
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(data, Data);
        }
    }
}
