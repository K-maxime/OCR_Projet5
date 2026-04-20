import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { FeedComponent } from './pages/feed/feed.component';
import { NewPostComponent } from './pages/new-post/new-post.component';
import { PostComponent } from './pages/post/post.component';
import { SubjectsComponent } from './pages/subjects/subjects.component';
import { UserComponent } from './pages/user/user.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { RegisterComponent } from './pages/register/register.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },  
  { path: 'home', component: HomeComponent },
  { path: 'register', component: RegisterComponent},
  { path: 'login', component: LoginComponent},
  { path: 'feed', component: FeedComponent},
  { path: 'new-post', component: NewPostComponent},
  { path: 'post/:id', component: PostComponent},
  { path: 'subjects', component: SubjectsComponent},
  { path: 'user', component: UserComponent},  
  { path: 'not-found', component: NotFoundComponent},


];