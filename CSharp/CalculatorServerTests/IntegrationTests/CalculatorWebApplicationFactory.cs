using CalculatorServer;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Mvc.Testing;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;

namespace CalculatorServerTests
{
    public class CalculatorWebApplicationFactory<TStartup>
    : WebApplicationFactory<TStartup> where TStartup : class
    {
   
        protected override void ConfigureWebHost(IWebHostBuilder builder)
        {
            builder.ConfigureServices(services =>
            {
                // Create a new service provider.
                var serviceProvider = new ServiceCollection()
                .AddEntityFrameworkInMemoryDatabase()
                .BuildServiceProvider();

                // Add a database context (UserDbContext) using an in-memory 
                // database for testing.
                services.AddDbContext<UserDbContext>(options =>
                    {
                        options.UseInMemoryDatabase("InMemoryDbForTesting");
                        options.UseInternalServiceProvider(serviceProvider);
                    });

                // Build the service provider.
                var sp = services.BuildServiceProvider();
                
                // Create a scope to obtain a reference to the database
                // context (UserDbContext).
                using (var scope = sp.CreateScope())
                {
                    var scopedServices = scope.ServiceProvider;
                    var db = scopedServices.GetRequiredService<UserDbContext>();

                    // Ensure the database is created.
                    db.Database.EnsureCreated();

                    DatabaseInitializer.Initialize(db);
                }
            });
        }

    }
}
