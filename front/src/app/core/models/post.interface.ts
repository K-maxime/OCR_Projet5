import { Subject } from "./subjects.interface";
import { UserInformation } from "./userInformation.interface";
import { CommentInformation } from "./commentInformation.interface";

export interface Post {
    id: number;
    title: string;
    content: string;
    createdAt: Date;

    subject: Subject;
    author: UserInformation;
    comments: CommentInformation[];
}