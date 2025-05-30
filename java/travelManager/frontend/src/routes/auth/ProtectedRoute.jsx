import {useCurrentUser} from "@/lib/session.js";
import {useLocation, useNavigate} from "react-router";
import {useEffect, useState} from "react";

export default function ProtectedRoute({children, allowedRoles = []}) {
    const user = useCurrentUser();
    const navigate = useNavigate();
    const location = useLocation();
    const [loaded, setLoaded] = useState(false);

    const publicPaths = ["/accounts/signin", "/accounts/signup"];
    const isPublicPath = publicPaths.includes(location.pathname);
    const userRoles = user?.roles || [];

    const hasAccess =
        allowedRoles.length === 0 || allowedRoles.some((role) => userRoles.includes(role));

    useEffect(() => {
        setLoaded(true);
    }, []);

    useEffect(() => {
        if (!loaded) return;

        if (!user && !isPublicPath) {
            navigate("/accounts/signin");
        } else if (user && allowedRoles.length > 0 && !hasAccess) {
            navigate("/");
        }
    }, [loaded, user, isPublicPath, allowedRoles, hasAccess, navigate]);

    if (!loaded) return null;

    return children;
}
