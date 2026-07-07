import UserProfile from "../models/UserProfile";
import Organization from "../models/Organization";
import User from "../models/User";
import WSReader from "../rocket/WSReader";
import MessageDispatch from "../rocket/MessageDispatch";
import { RPCConstants } from "../rocket/D3ETemplate";

export default class UniqueCheckerClient {
  public async checkNameUniqueInOrganization(
    organization: Organization,
    name: string
  ): Promise<boolean> {
    let r: WSReader = await MessageDispatch.get().rpcMessage(
      RPCConstants.UniqueChecker,
      RPCConstants.UniqueCheckerCheckNameUniqueInOrganization,
      {
        "args": (w) => {
          w.writeObjFull(organization);

          w.writeString(name);
        },
      }
    );

    let code: number = r.readByte();

    if (code === 1) {
      let error: string = r.readString();

      return Promise.error(error);
    }

    return r.readBoolean() as boolean;
  }
  public async checkUserEmailUniqueInOrganization(
    organization: Organization,
    user: User,
    email: string
  ): Promise<boolean> {
    let r: WSReader = await MessageDispatch.get().rpcMessage(
      RPCConstants.UniqueChecker,
      RPCConstants.UniqueCheckerCheckUserEmailUniqueInOrganization,
      {
        "args": (w) => {
          w.writeObjFull(organization);

          w.writeObjFull(user);

          w.writeString(email);
        },
      }
    );

    let code: number = r.readByte();

    if (code === 1) {
      let error: string = r.readString();

      return Promise.error(error);
    }

    return r.readBoolean() as boolean;
  }
  public async checkUserProfileUserUniqueInOrganization(
    organization: Organization,
    userProfile: UserProfile,
    user: User
  ): Promise<boolean> {
    let r: WSReader = await MessageDispatch.get().rpcMessage(
      RPCConstants.UniqueChecker,
      RPCConstants.UniqueCheckerCheckUserProfileUserUniqueInOrganization,
      {
        "args": (w) => {
          w.writeObjFull(organization);

          w.writeObjFull(userProfile);

          w.writeObjFull(user);
        },
      }
    );

    let code: number = r.readByte();

    if (code === 1) {
      let error: string = r.readString();

      return Promise.error(error);
    }

    return r.readBoolean() as boolean;
  }
}
