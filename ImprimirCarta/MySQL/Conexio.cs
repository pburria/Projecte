using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Text;

namespace MySQL
{
    class Conexio : DbContext
    {
        protected override void OnConfiguring(DbContextOptionsBuilder optionBuilder)
        {
            optionBuilder.UseMySQL("Server=51.68.224.27;Database=dam2_pburria;UID=dam2-pburria;Password=47110807E");
        }
    }
}
