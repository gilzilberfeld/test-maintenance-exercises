using System;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web;
using Xunit;
using Xunit.Gherkin.Quick;

namespace CalculatorServerTests
{
    [FeatureFile("CucumberTests\\CucumberCalculatorTests.feature")]
    public class CucumberTestsStepDefinitions : Feature, IDisposable
    {
        private TestServerFactory factory;
        private HttpClient client;


        public CucumberTestsStepDefinitions() : base()
        {
            factory = new TestServerFactory();
            client = factory.CreateClient();
        }

        [Given(@"A reset calculator")]
        public async Task GivenAResetCalculator()
        {
            await client.PostAsync("calculator/reset", null);
        }

        [When(@"The user presses (.*)")]
        [And(@"The user presses (.*)")]
        public async Task WhenTheUserPresses(string key)
        {
            string url = "calculator/press?key=" + GetEncodedKey(key);
            await client.PostAsync(url, null);
        }

        [Then(@"The calculator displays (.*)")]
        public async Task ThenTheCalculatorDisplays(string display)
        {
            var response = await client.GetAsync("calculator/display");
            Assert.Equal("3", response.Content.ReadAsStringAsync().Result);
        }

        private static string GetEncodedKey(string key)
        {
            string realKey = key.Substring(1, 1);
            return HttpUtility.UrlEncode(realKey);
        }


        public void Dispose()
        {
            factory.Dispose();
            client.Dispose();
        }

    }
}
