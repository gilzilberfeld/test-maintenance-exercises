using CalculatorServer;
using System;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web;
using Xunit;

namespace CalculatorServerTests
{

    public class IntegrationTests : IDisposable     
    {
        private TestServerFactory factory;
        private HttpClient client;


        public IntegrationTests() 
        {
            factory = new TestServerFactory();
            client = factory.CreateClient();
            PostSync("calculator/reset");
        }




        [Fact]
        public async void OverflowAfterMultiplePresses()
        {
            string url = "calculator/press?key=" + GetEncodedKey("9");
            await client.PostAsync(url, null);
            await client.PostAsync(url, null);
            await client.PostAsync(url, null);
            await client.PostAsync(url, null);
            await client.PostAsync(url, null);
            await client.PostAsync(url, null);

            var response = await client.GetAsync("calculator/display");
            Assert.Equal("E", response.Content.ReadAsStringAsync().Result);

        }

        [Fact]
        public async void DivisionByZero()
        {
            string url = "calculator/press?key=" + GetEncodedKey("9");
            await client.PostAsync(url, null);
            url = "calculator/press?key=" + GetEncodedKey("/");
            await client.PostAsync(url, null);
            url = "calculator/press?key=" + GetEncodedKey("0");
            await client.PostAsync(url, null);
            url = "calculator/press?key=" + GetEncodedKey("=");
            await client.PostAsync(url, null);

            var response = await client.GetAsync("calculator/display");
            Assert.Equal("E", response.Content.ReadAsStringAsync().Result);
        }

        [Fact(Skip = "Not implemented")]
        public async void CalculationsWithLastValueSaved()
        {
            var response = await client.GetAsync("calculator/stored?user=Gil");

            string url = "calculator/press?key=" + GetEncodedKey(response.Content.ReadAsStringAsync().Result);
            await client.PostAsync(url, null);
            url = "calculator/press?key=" + GetEncodedKey("+");
            await client.PostAsync(url, null);
            url = "calculator/press?key=" + GetEncodedKey("3");
            await client.PostAsync(url, null);
            url = "calculator/press?key=" + GetEncodedKey("=");
            await client.PostAsync(url, null);

            response = await client.GetAsync("calculator/display");
            Assert.Equal("5", response.Content.ReadAsStringAsync().Result);
        }

        [Fact]
        public void AfterOperation()
        {
            Calculator calc = new Calculator(null);
            calc.Press("2");
            calc.Press("2");
            calc.Press("+");
            String result = calc.Display;
            Assert.Equal("22", result);
        }


        [Fact]
        public async Task RestoreByUser()
        {
            var url = "calculator/restore?user=Gil";
            await client.PostAsync(url, null);
            var response = await client.GetAsync("calculator/display");
            Assert.Equal("2", response.Content.ReadAsStringAsync().Result);
        }

        [Fact]
        public async Task RestoreByUserAndContinue()
        {
            var url = "calculator/restore?user=Gil";
            await client.PostAsync(url, null);
            url = "calculator/press?key=" + GetEncodedKey("+");
            await client.PostAsync(url, null);
            var response = await client.GetAsync("calculator/display");
            Assert.Equal("2", response.Content.ReadAsStringAsync().Result);
        }

        [Fact(Skip ="Not implemented")]
        public async Task MultiParamaterCalculation()
        {
            string url = "calculator/press?key=" + GetEncodedKey("3");
            await client.PostAsync(url, null);
            url = "calculator/press?key=" + GetEncodedKey("-");
            await client.PostAsync(url, null);
            url = "calculator/press?key=" + GetEncodedKey("5");
            await client.PostAsync(url, null);
            url = "calculator/press?key=" + GetEncodedKey("+");
            await client.PostAsync(url, null);
            url = "calculator/press?key=" + GetEncodedKey("9");
            await client.PostAsync(url, null);
            url = "calculator/press?key=" + GetEncodedKey("=");
            await client.PostAsync(url, null);
            var response = await client.GetAsync("calculator/display");
            Assert.Equal("7", response.Content.ReadAsStringAsync().Result);
        }

        [Fact]
        public async Task SimpleCalcuation()
        {
            string url;

            url = "calculator/press?key=" + GetEncodedKey("1");
            await client.PostAsync(url, null);
            url = "calculator/press?key=" + GetEncodedKey("+");
            await client.PostAsync(url, null);
            url = "calculator/press?key=" + GetEncodedKey("2");
            await client.PostAsync(url, null);
            url = "calculator/press?key=" + GetEncodedKey("=");
            await client.PostAsync(url, null);
            var response = await client.GetAsync("calculator/display");
            Assert.Equal("3", response.Content.ReadAsStringAsync().Result);
        }

        [Fact]
        public async Task PressingOpKeyDoesntChangeDisplay()
        {
            string url = "calculator/press?key=" + GetEncodedKey("6");
            await client.PostAsync(url, null);
            url = "calculator/press?key=" + GetEncodedKey("+");
            await client.PostAsync(url, null);

            var response = await client.GetAsync("calculator/display");
            Assert.Equal("6", response.Content.ReadAsStringAsync().Result);

        }

        [Fact]
        public async Task AnotherSimpleCalculation()
        {
            string url = "calculator/press?key=" + GetEncodedKey("5");
            await client.PostAsync(url, null);
            url = "calculator/press?key=" + GetEncodedKey("+");
            await client.PostAsync(url, null);
            url = "calculator/press?key=" + GetEncodedKey("3");
            await client.PostAsync(url, null);
            url = "calculator/press?key=" + GetEncodedKey("=");
            await client.PostAsync(url, null);
            var response = await client.GetAsync("calculator/display");
            Assert.Equal("8", response.Content.ReadAsStringAsync().Result);

        }


        private static string GetEncodedKey(string sign)
        {
            return HttpUtility.UrlEncode(sign);
        }

        private void PostSync(string url)
        {
            Task<HttpResponseMessage> task =
                            Task.Run<HttpResponseMessage>(async () => await client.PostAsync(url, null));
        }

        public void Dispose()
        {
            factory.Dispose();
            client.Dispose();
        }
    }
}
