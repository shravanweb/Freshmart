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
  public static token: string;
  public static get(): ApolloClient<any> {
    if (!GraphQLClientInit._client) {
      const _httpLink = new HttpLink({
        uri: Env.get().resolvedHttpUrl + "/api/native/graphql",
      });
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
