using ApprovalTests;
using ApprovalTests.Reporters;
using System;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Web;
using Xunit;

namespace CalculatorServerTests
{
    public class ApprovalTests : IDisposable
    {
        private TestServerFactory factory;
        private HttpClient client;
        private StringBuilder log;

        public ApprovalTests()
        {
            factory = new TestServerFactory();
            client = factory.CreateClient();
            PostSync("calculator/reset");
            log = new StringBuilder();
        }


        [Fact(Skip = "Don't want to ruin the rest of the tests")]
        [UseReporter(typeof(DiffReporter))]
        public void GetWithApprovals()
        {
            string result = GetSync("calculator/display");
            Approvals.Verify(result);

        }

        [Fact(Skip = "Don't want to ruin the rest of the tests")]
        [UseReporter(typeof(DiffReporter))]
        public void OperationsWithCalculator()
        {
            Reset();
            PressSequence("1+2=");
            Reset();
            PressSequence("1+C");
            Approvals.Verify(log.ToString());
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

        private void PressSequence(String sequence)
        {
            foreach (char c in sequence)
            {
                Press(c);
            }
        }

        private void Press(char key)
        {
            String result;
            try
            {
                string url = "calculator/press?key=" + GetEncodedKey(key.ToString());
                PostSync(url);
                result = GetSync("calculator/display");
            }
            catch (Exception e)
            {
                result = "Error";
            }

            log.Append(AddPressResult(key, result));
        }


        private void Reset()
        {
            PostSync("/calculator/press?key=" + GetEncodedKey("C"));
            log.Append("Reset\n");
        }

        private String AddPressResult(char key, String display)
        {
            return "Pressed " + key + ", Display shows: " + display + "\n";
        }

        private static string GetEncodedKey(string key)
        {
            return HttpUtility.UrlEncode(key);
        }

        public void Dispose()
        {
            factory.Dispose();
            client.Dispose();
        }

    }
}
