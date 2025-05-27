import {BrowserRouter, Route, Routes} from "react-router-dom";
import Home from "./pages/home";
import Studies from "./pages/studies";
import Configurations from "./pages/configurations";
import Login from "./pages/login";
import NotFound from "./pages/not-found";
import Layout from "./components/layout/layout";
import {AuthenticationProvider} from "./services/authentication";
import Imports from "./pages/imports";
import Exports from "./pages/exports";
import Mappings from "./pages/mappings";
import Indicators from "./pages/indicators";
import Messages from "./pages/messages";
import Users from "./pages/users";
import User from "./pages/user";

const App = () => {

  return (
      <AuthenticationProvider>
          <BrowserRouter>
              <Routes>
                  <Route path="/login" Component={Login} />
                  <Route path="*" Component={NotFound} />
                  <Route Component={Layout}>
                      <Route path="/" Component={Home} />
                      <Route path="/studies" Component={Studies} />
                      <Route path="/imports" Component={Imports} />
                      <Route path="/exports" Component={Exports} />
                      <Route path="/mappings" Component={Mappings} />
                      <Route path="/indicators" Component={Indicators} />
                      <Route path="/messages" Component={Messages} />
                      <Route path="/users" Component={Users} />
                      <Route path="/configurations" Component={Configurations} />
                      <Route path="/user" Component={User} />
                  </Route>
              </Routes>
          </BrowserRouter>
      </AuthenticationProvider>
  )
}

export default App
