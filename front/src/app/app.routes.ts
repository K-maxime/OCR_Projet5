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
import { AuthGuard } from './guards/Auth.guard';

export const routes: Routes = [

  { path: 'home', component: HomeComponent },
  { path: 'register', component: RegisterComponent},
  { path: 'login', component: LoginComponent},
  { path: 'not-found', component: NotFoundComponent},
  
  { path: 'feed', component: FeedComponent, canActivate: [AuthGuard]},
  { path: 'new-post', component: NewPostComponent, canActivate: [AuthGuard]},
  { path: 'post/:id', component: PostComponent, canActivate: [AuthGuard]},
  { path: 'subjects', component: SubjectsComponent, canActivate: [AuthGuard]},
  { path: 'user', component: UserComponent, canActivate: [AuthGuard]},  
  

  { path: '', redirectTo: 'home', pathMatch: 'full' }


];