using CalculatorServer;
using JetBrains.Annotations;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.ChangeTracking;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;

namespace CalculatorServerTests
{
    internal class MockUserDbContext : UserDbContext
    {
        internal User mockUser;
    }

}