import {useAuthentication} from "../services/authentication";
import {Link, Navigate, Outlet, useLocation, useNavigate} from "react-router-dom";
import {
    Breadcrumb, BreadcrumbItem,
    Content,
    Header, HeaderContainer,
    HeaderGlobalAction,
    HeaderGlobalBar, HeaderMenu, HeaderMenuButton,
    HeaderMenuItem,
    HeaderName,
    HeaderNavigation, HeaderSideNavItems, SideNav, SideNavItems
} from "@carbon/react";
import {Chat, Settings, UserMultiple} from "@carbon/icons-react";

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
                    <HeaderName prefix="KSA" as={Link} to="/">OpenREM</HeaderName>
                    <HeaderNavigation>
                        <HeaderMenuItem as={Link} to="/studies">Studies</HeaderMenuItem>
                        <HeaderMenuItem as={Link} to="/imports">Imports</HeaderMenuItem>
                        <HeaderMenuItem as={Link} to="/exports">Exports</HeaderMenuItem>
                        <HeaderMenuItem as={Link} to="/mappings">Mappings</HeaderMenuItem>
                        <HeaderMenuItem as={Link} to="/indicators">Indicators</HeaderMenuItem>
                    </HeaderNavigation>
                    <HeaderGlobalBar>
                        <HeaderGlobalAction onClick={() => navigate("/messages")} aria-label="Messages">
                            <Chat/>
                        </HeaderGlobalAction>
                        <HeaderGlobalAction onClick={() => navigate("/users")} aria-label="Users">
                            <UserMultiple/>
                        </HeaderGlobalAction>
                        <HeaderGlobalAction onClick={() => navigate("/settings")} aria-label="Settings">
                            <Settings/>
                        </HeaderGlobalAction>
                    </HeaderGlobalBar>
                    <SideNav
                        aria-label="Side Navigation"
                        expanded={isSideNavExpanded}
                        isPersistent={false}
                        onSideNavBlur={onClickSideNavExpand}>
                        <SideNavItems>
                            <HeaderSideNavItems>
                                <HeaderMenuItem as={Link} to="/" onClick={onClickSideNavExpand}>Home</HeaderMenuItem>
                                <HeaderMenuItem as={Link} to="/studies"
                                                onClick={onClickSideNavExpand}>Studies</HeaderMenuItem>
                            </HeaderSideNavItems>
                        </SideNavItems>
                    </SideNav>
                </Header>
                <Content>
                    <Breadcrumb>
                        {location.pathname.split("/").map((path, index) => index > 0 ? (
                                <BreadcrumbItem key={path}>
                                    <Link to={path}>{path}</Link>
                                </BreadcrumbItem>
                            ) : null
                        )}
                    </Breadcrumb>
                    <Outlet/>
                </Content>
            </>
        )}/> : <Navigate to="/login"/>
}

export default Layout
