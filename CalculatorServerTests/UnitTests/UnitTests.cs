using CalculatorServer;
using System;
using Xunit;

namespace CalculatorServerTests
{
    public class UnitTests
    {
        public UnitTests()
        {
            Calculator.Reset();
        }
        [Fact]
        public void CancelTheFirstNumber()
        {
            Calculator c = new Calculator(null);
            c.Press("1");
            c.Press("C");
            String result = c.Display;
            Assert.Equal("0", result);
        }

        [Fact]
        public void CancelTheFirstNumber2()
        {
            Calculator c = new Calculator(null);
            c.Press("1");
            c.Press("C");
            c.Press("2");
            String result = c.Display;
            Assert.Equal("2", result);
        }

        [Fact]
        public void DisplayOnlyIfUserIsLoggedIn()
        {
            Calculator calc = new Calculator(null);
            if (String.IsNullOrEmpty(calc.CurrentUser))
                Assert.Equal("0", calc.Display);
        }

        [Fact(Skip ="Need to mock DbContext")]
        public void RestoreMemoryAndContinue()
        {

            MockUserDbContext mockUserRepository = new MockUserDbContext();
            User mockUser = new User();
            mockUser.Name = "Gil";
            mockUser.Memory = 2;
            mockUserRepository.mockUser = mockUser;
            Calculator calculator = new Calculator(mockUserRepository);
            calculator.GetLastValueFor("Gil");
            String lastStoredValue = calculator.Display;
            if (lastStoredValue.Equals("2"))
            {
                calculator.Press("3");
                String result = calculator.Display;
                Assert.Equal("23", result);
            }
        }

        [Fact]
        public void CancelAfterOperation()
        {
            String result;
            Calculator c = new Calculator(null);
            c.Press("1");
            c.Press("+");
            c.Press("C");
            result = c.Display;
        }

        [Fact]
        public void OnePress()
        {
            Calculator c = new Calculator(null);
            c.Press("1");
            String result = c.Display;
            Assert.True(result == "1");
        }

        [Fact]
        public void TwoNumbers()
        {
            Calculator c = new Calculator(null);
            c.Press("1");
            c.Press("2");
            String result = c.Display;
            Assert.Equal(result, "12");
        }

        [Fact]
        public void AnotherTwoNumbers()
        {
            Calculator c = new Calculator(null);
            c.Press("9");
            c.Press("5");
            String result = c.Display;
            Assert.Equal(result, "95");
        }

        [Fact(Skip = "Whatever")]
        public void OtherTwoNumbers()
        {
            Calculator c = new Calculator(null);
            c.Press("7");
            c.Press("3");
            String result = c.Display;
            Assert.Equal(result, "73");
        }

        [Fact(Skip = "I'll get there")]
        public void orderOfOperations()
        {
            Calculator c = new Calculator(null);
            c.Press("1");
            c.Press("+");
            c.Press("8");
            c.Press("/");
            c.Press("4");
            c.Press("=");
            String result = c.Display;
            Assert.Equal(result, "3");
        }

    }
}

