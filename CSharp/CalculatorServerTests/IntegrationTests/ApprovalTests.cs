using ApprovalTests;
using ApprovalTests.Reporters;
using System;
using System.Net.Http;
using System.Threading.Tasks;
using Xunit;

namespace CalculatorServerTests
{
    public class ApprovalTests : IDisposable
    {
        private TestServerFactory factory;
        private HttpClient client;


        public ApprovalTests()
        {
            factory = new TestServerFactory();
            client = factory.CreateClient();
            PostSync("calculator/reset");
        }


        [Fact(Skip = "Don't want to ruin the rest of the tests")]
        [UseReporter(typeof(DiffReporter))]
        public void GetWithApprovals()
        {
            string result = GetSync("calculator/display");
            Approvals.Verify(result);

        }

        private string GetSync(string url)
        {
            Task<HttpResponseMessage> task =
                            Task.Run<HttpResponseMessage>(async () => await client.GetAsync(url));
            return task.Result.Content.ReadAsStringAsync().Result;
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
