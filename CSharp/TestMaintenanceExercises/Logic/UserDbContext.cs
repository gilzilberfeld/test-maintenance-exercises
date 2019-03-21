using Microsoft.EntityFrameworkCore;

namespace CalculatorServer
{
    public class UserDbContext : DbContext
    {
        public UserDbContext(DbContextOptions<UserDbContext> options)
            : base(options)
                { }
        public UserDbContext() : base() { }
        public virtual DbSet<User> Users { get; set; }
    }
}
