using CalculatorServer;

namespace CalculatorServerTests
{
    class DatabaseInitializer
    {
        public static void Initialize(UserDbContext db)
        {
            User user = new User();
            user.Name = "Gil";
            user.Memory = 2;

            db.Users.Add(user);
            db.SaveChanges();

        }
    }
}
