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
        /*
        public static List<Catagoria> getEquipsAmbJugadors()
        {

            try
            {
                using (Formula1BD context = new Formula1BD())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();

                        using (var consulta = connexio.CreateCommand())
                        {
                            consulta.CommandText = @"select e.*
                                                    from equipos e left join pilotos p on e.EQUIP_COD = p.EQUIP_PILOT 
                                                    where p.EQUIP_PILOT>0";

                            DbDataReader reader = consulta.ExecuteReader();

                            List<Catagoria> equips = new List<Catagoria>();
                            while (reader.Read())
                            {

                                int equip_cod = reader.GetInt32(reader.GetOrdinal("EQUIP_COD"));
                                string full_name = reader.GetString(reader.GetOrdinal("FULL_NAME"));
                                string team_name = reader.GetString(reader.GetOrdinal("TEAM_NAME"));
                                string team_chief = reader.GetString(reader.GetOrdinal("TEAM_CHIEF"));
                                string chassis = reader.GetString(reader.GetOrdinal("CHASSIS"));
                                string powerUnit = reader.GetString(reader.GetOrdinal("POWER_UNIT"));
                                int puntuacio = reader.GetInt32(reader.GetOrdinal("PUNTUACIO"));
                                int firstTeamEntry = reader.GetInt32(reader.GetOrdinal("FIRST_TEAM_ENTRY"));
                                int worldChampionship = reader.GetInt32(reader.GetOrdinal("WORLD_CHAMPIONSHIP"));
                                int fastedLaps = reader.GetInt32(reader.GetOrdinal("FASTEST_LAPS"));
                                string pneumatics = reader.GetString(reader.GetOrdinal("NEUMATICOS"));
                                string pais = reader.GetString(reader.GetOrdinal("PAIS_EQUIP"));
                                string descr = reader.GetString(reader.GetOrdinal("DESCR"));
                                string icon = reader.GetString(reader.GetOrdinal("ICONA"));
                                string cotxe = reader.GetString(reader.GetOrdinal("COTXE"));
                                Catagoria e = new Catagoria(equip_cod, full_name, team_chief, team_name, chassis, powerUnit, firstTeamEntry, worldChampionship,
                                    fastedLaps, pneumatics, pais, descr, puntuacio,icon,cotxe);
                                equips.Add(e);
                            }
                            return equips;
                        }
                    }
                }

            }
            catch (Exception)
            {

            }
            return null;
        }

        public static int getPoints(int id)
        {
            Int32 Points = 0;

            try
            {
                using (Formula1BD context = new Formula1BD())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();

                        using (var consulta = connexio.CreateCommand())
                        {
                            consulta.CommandText = @"select puntuacio
                                                    from equipos
                                                    where equip_cod=@id";
                            BDUtils.crearParametre(consulta, "id", System.Data.DbType.Int32, id);

                            var r = consulta.ExecuteScalar();
                            Points = (Int32)r;

                            return Points;
                        }
                    }
                }

            }
            catch (Exception)
            {

            }
            return -1;
        }
        public static bool UpdateEquips(int id,string name,string fullName,string teamChief,string chassis,string powerUnit,
                                                     int firstTeamEntry,int worldChampionship,int fastedLaps,string desc)
        {

            try
            {
                DbTransaction trans = null;

                using (Formula1BD context = new Formula1BD())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();

                        using (var consulta = connexio.CreateCommand())
                        {
                            trans = connexio.BeginTransaction();
                            consulta.Transaction = trans;
                            consulta.CommandText = @"update equipos
                                                     set full_name=@fullName,
                                                     team_chief =@teamChief,
                                                     TEAM_NAME =@name,
                                                     CHASSIS =@chassis,
                                                     POWER_UNIT =@powerUnit,
                                                     FIRST_TEAM_ENTRY =@firstTeamEntry,
                                                     WORLD_CHAMPIONSHIP =@worldChampionship,
                                                     FASTEST_LAPS =@fastedLaps,
                                                     DESCR =@desc
                                                     where equip_cod=@id";
                            BDUtils.crearParametre(consulta, "id", System.Data.DbType.Int32, id);
                            BDUtils.crearParametre(consulta, "fullName", System.Data.DbType.String, fullName);
                            BDUtils.crearParametre(consulta, "teamChief", System.Data.DbType.String, teamChief);
                            BDUtils.crearParametre(consulta, "name", System.Data.DbType.String, name);
                            BDUtils.crearParametre(consulta, "chassis", System.Data.DbType.String, chassis);
                            BDUtils.crearParametre(consulta, "powerUnit", System.Data.DbType.String, powerUnit);
                            BDUtils.crearParametre(consulta, "firstTeamEntry", System.Data.DbType.Int32, firstTeamEntry);
                            BDUtils.crearParametre(consulta, "worldChampionship", System.Data.DbType.Int32, worldChampionship);
                            BDUtils.crearParametre(consulta, "fastedLaps", System.Data.DbType.Int32, fastedLaps);
                            BDUtils.crearParametre(consulta, "desc", System.Data.DbType.String, desc);
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

        public static bool UpdatePuntsEquips(int id,int pts)
        {

            try
            {
                DbTransaction trans = null;

                using (Formula1BD context = new Formula1BD())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();

                        using (var consulta = connexio.CreateCommand())
                        {
                            trans = connexio.BeginTransaction();
                            consulta.Transaction = trans;
                            consulta.CommandText = @"update equipos
                                                    set PUNTUACIO=@pts
                                                    where equip_cod=@id";
                            BDUtils.crearParametre(consulta, "id", System.Data.DbType.Int32, id);
                            BDUtils.crearParametre(consulta, "pts", System.Data.DbType.Int32, pts);

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



        */
    }
}
