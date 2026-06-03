import { UserInformation } from "./userInformation.interface";

export interface CommentInformation {
    id: number;
    content: string;
    author: UserInformation;
    createdAt: Date;
}