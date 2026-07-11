import {
  ApolloClient,
  ApolloLink,
  concat,
  HttpLink,
  InMemoryCache,
} from "@apollo/client";
import Env from "../classes/Env";

export default class GraphQLClientInit {
  private static _client: ApolloClient<any>;
  private static _uri = "";
  public static token: string;
  public static get(): ApolloClient<any> {
    const uri = Env.get().resolvedHttpUrl + "/api/native/graphql";
    if (!GraphQLClientInit._client || GraphQLClientInit._uri !== uri) {
      GraphQLClientInit._uri = uri;
      const _httpLink = new HttpLink({ uri });
      const _auth = new ApolloLink((operation, forward) => {
        if (GraphQLClientInit.token != null) {
          operation.setContext({
            headers: {
              authorization: "Bearer " + GraphQLClientInit.token,
            },
          });
        }
        return forward(operation);
      });
      GraphQLClientInit._client = new ApolloClient<any>({
        link: concat(_auth, _httpLink),
        cache: new InMemoryCache(),
      });
    }
    return GraphQLClientInit._client;
  }
}
