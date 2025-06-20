import { createBrowserRouter } from 'react-router-dom';
import MainLayout from "@/layouts/MainLayout.jsx";
import { ThemeProvider } from "@/context/ThemeContext.jsx";
import NotFoundRoute from "./routes/NotFoundRoute.jsx";
import ProtectedRoute from "./routes/auth/ProtectedRoute";
import SignInRoute from './routes/auth/SignInRoute';
import SignUpRoute from './routes/auth/SignUpRoute';
import AuthLayout from "@/layouts/AuthLayout.jsx";
import CreateAdminRoute from "@/routes/admin/CreateAdminRoute.jsx";
import ManageAccountsRoute from "@/routes/admin/ManageAccountsRoute.jsx";
import TripOverviewRoute from "@/routes/dashboard/TripOverviewRoute.jsx";
import CreateTripRoute from "@/routes/dashboard/CreateTripRoute.jsx";

const router = createBrowserRouter([
    {
        element: (
            <ProtectedRoute>
                <ThemeProvider>
                    <MainLayout />
                </ThemeProvider>
            </ProtectedRoute>
        ),
        children: [
            {
                path: "trips/create",
                element: <CreateTripRoute/>
            },
            {
                path: "/trips/all",
                element: <TripOverviewRoute />
            },
            {
                path: "/admin/create-admin",
                element: (
                    <ProtectedRoute allowedRoles={["ADMIN"]}>
                        <CreateAdminRoute />
                    </ProtectedRoute>
                ),
            },
            {
                path: "/admin/manage-accounts",
                element: (
                    <ProtectedRoute allowedRoles={["ADMIN"]}>
                        <ManageAccountsRoute />
                    </ProtectedRoute>
                ),
            },
            {
                path: "*",
                element: <NotFoundRoute />,
            },
        ],
    },
    {
        path: "/accounts/signin",
        element: (
            <AuthLayout>
                <SignInRoute />
            </AuthLayout>
        ),
        action: SignInRoute.action,
    },
    {
        path: "/accounts/signup",
        element: (
            <AuthLayout>
                <SignUpRoute />
            </AuthLayout>
        ),
        action: SignUpRoute.action,
    },
]);

export default router;
