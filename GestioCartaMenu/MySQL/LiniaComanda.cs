using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data.Common;
using System.Text;

namespace MySQL
{
    public class LiniaComanda : INotifyPropertyChanged

    {
        public event PropertyChangedEventHandler PropertyChanged;
        private int comanda;
        private int plat;
        private int numLiniaComanda;
        private int qtat;
        private bool acabat;
        private string nomPlat;

        public LiniaComanda(int comanda, int plat, int numLiniaComanda, int qtat, bool acabat, string nomPlat)
        {
            this.Comanda = comanda;
            this.Plat = plat;
            this.NumLiniaComanda = numLiniaComanda;
            this.Qtat = qtat;
            this.Acabat = acabat;
            this.NomPlat = nomPlat;
        }

        public int Comanda { get => comanda; set => comanda = value; }
        public int Plat { get => plat; set => plat = value; }
        public int NumLiniaComanda { get => numLiniaComanda; set => numLiniaComanda = value; }
        public int Qtat { get => qtat; set => qtat = value; }
        public bool Acabat
        {
            get { return acabat; }
            set
            {
                acabat = value;
                PropertyChanged?.Invoke(this, new PropertyChangedEventArgs("acabat"));
            }
        }
        public string NomPlat { get => nomPlat; set => nomPlat = value; }

        public static List<LiniaComanda> getLiniaComandaPerComanda(int comanda)
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
                            consulta.CommandText = @"select l.*,p.NOM 
                                                     from linia_comanda l join plat p on l.PLAT=p.CODI
                                                     where comanda=@comanda";
                            BDUtils.crearParametre(consulta, "comanda", System.Data.DbType.Int32, comanda);

                            DbDataReader reader = consulta.ExecuteReader();

                            List<LiniaComanda> linies = new List<LiniaComanda>();
                            while (reader.Read())
                            {
                                int comandaa = reader.GetInt32(reader.GetOrdinal("COMANDA"));
                                int plat = reader.GetInt32(reader.GetOrdinal("PLAT"));
                                int num = reader.GetInt32(reader.GetOrdinal("NUM"));
                                int qtat = reader.GetInt32(reader.GetOrdinal("QUANTITAT"));
                                bool disponible = reader.GetBoolean(reader.GetOrdinal("ACABAT"));
                                string nom = reader.GetString(reader.GetOrdinal("NOM"));
                                LiniaComanda linia = new LiniaComanda(comandaa, plat, num, qtat, disponible, nom);
                                linies.Add(linia);
                            }
                            return linies;
                        }
                    }
                }
            }
            catch (Exception)
            {

            }
            return null;
        }

        public static long GetNumeroPlatsNoAcabats(int comanda)
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
                                                    from linia_comanda
                                                    where comanda = @comanda and ACABAT = false";
                            BDUtils.crearParametre(consulta, "comanda", System.Data.DbType.Int32, comanda);

                            var r = consulta.ExecuteScalar();
                            tamany = (Int64)r;
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



        public static bool UpdatePlatAcabat(int plat,int comanda)
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
                            consulta.CommandText = @"update linia_comanda
                                                     set acabat=true
                                                     where plat=@plat and comanda=@comanda";
                            BDUtils.crearParametre(consulta, "plat", System.Data.DbType.Int32, plat);
                            BDUtils.crearParametre(consulta, "comanda", System.Data.DbType.Int32, comanda);
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

        public override bool Equals(object obj)
        {
            return obj is LiniaComanda comanda &&
                   acabat == comanda.acabat &&
                   Acabat == comanda.Acabat;
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(acabat, Acabat);
        }
    }

}
