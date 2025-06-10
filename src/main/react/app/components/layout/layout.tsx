import {useAuthentication} from "../../services/authentication";
import {Link, Navigate, Outlet, useLocation, useNavigate} from "react-router-dom";
import {
    Breadcrumb, BreadcrumbItem, Column,
    Content, FlexGrid, Grid,
    Header, HeaderContainer,
    HeaderGlobalAction,
    HeaderGlobalBar, HeaderMenu, HeaderMenuButton,
    HeaderMenuItem,
    HeaderName,
    HeaderNavigation, HeaderSideNavItems, Heading, Row, SideNav, SideNavItems, Stack
} from "@carbon/react";
import {Book, Chat, Gears, Settings, User, UserMultiple} from "@carbon/icons-react";
import Flex from "../flex/flex";

const Layout = () => {
    const authentication = useAuthentication();
    const navigate = useNavigate();
    const location = useLocation();

    return authentication.isAuthenticated ?
        <HeaderContainer render={({isSideNavExpanded, onClickSideNavExpand}) => (
            <>
                <Header>
                    <HeaderMenuButton
                        aria-label={isSideNavExpanded ? 'Close Menu' : 'Open Menu'}
                        onClick={onClickSideNavExpand}
                        isActive={isSideNavExpanded}
                        aria-expanded={isSideNavExpanded}
                    />
                    <HeaderName prefix="KSA" as={Link} to="/">NeoREM</HeaderName>
                    <HeaderNavigation>
                        <HeaderMenuItem as={Link} to="/studies">Studies</HeaderMenuItem>
                        <HeaderMenuItem as={Link} to="/devices">Devices</HeaderMenuItem>
                        <HeaderMenuItem as={Link} to="/patients">Patients</HeaderMenuItem>
                        <HeaderMenuItem as={Link} to="/imports">Imports</HeaderMenuItem>
                        <HeaderMenuItem as={Link} to="/exports">Exports</HeaderMenuItem>
                        <HeaderMenuItem as={Link} to="/mappings">Mappings</HeaderMenuItem>
                        <HeaderMenuItem as={Link} to="/indicators">Indicators</HeaderMenuItem>
                    </HeaderNavigation>
                    <HeaderGlobalBar>
                        <HeaderGlobalAction onClick={() => navigate("/documentation")} aria-label="Documenation">
                            <Book/>
                        </HeaderGlobalAction>
                        <HeaderGlobalAction onClick={() => navigate("/messages")} aria-label="Messages">
                            <Chat/>
                        </HeaderGlobalAction>
                        <HeaderGlobalAction onClick={() => navigate("/users")} aria-label="users">
                            <UserMultiple/>
                        </HeaderGlobalAction>
                        <HeaderGlobalAction onClick={() => navigate("/settings")} aria-label="Settings">
                            <Settings/>
                        </HeaderGlobalAction>
                        <HeaderGlobalAction onClick={() => navigate("/user")} aria-label="User">
                            <User/>
                        </HeaderGlobalAction>
                    </HeaderGlobalBar>
                    <SideNav
                        aria-label="Side Navigation"
                        expanded={isSideNavExpanded}
                        isPersistent={false}
                        onSideNavBlur={onClickSideNavExpand}>
                        <SideNavItems>
                            <HeaderSideNavItems>
                                <HeaderMenuItem as={Link} to="/studies">Studies</HeaderMenuItem>
                                <HeaderMenuItem as={Link} to="/devices">Devices</HeaderMenuItem>
                                <HeaderMenuItem as={Link} to="/patients">Patients</HeaderMenuItem>
                                <HeaderMenuItem as={Link} to="/imports">Imports</HeaderMenuItem>
                                <HeaderMenuItem as={Link} to="/exports">Exports</HeaderMenuItem>
                                <HeaderMenuItem as={Link} to="/mappings">Mappings</HeaderMenuItem>
                                <HeaderMenuItem as={Link} to="/indicators">Indicators</HeaderMenuItem>
                            </HeaderSideNavItems>
                        </SideNavItems>
                    </SideNav>
                </Header>
                <Content>
                    <Flex xl={{direction: "column", width: "full", height: "full", gap: 2}}>
                        <Breadcrumb>
                            {location.pathname.split("/").map((path, index) => index > 0 ? (
                                    <BreadcrumbItem key={path}>
                                        <Link to={path}>{path}</Link>
                                    </BreadcrumbItem>
                                ) : null
                            )}
                        </Breadcrumb>
                        <Outlet/>
                    </Flex>
                </Content>
            </>
        )}/> : <Navigate to="/login"/>
}

export default Layout
