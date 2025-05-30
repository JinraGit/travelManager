import {createBrowserRouter} from 'react-router-dom';
import MainLayout from "@/layouts/MainLayout.jsx";
import {ThemeProvider} from "@/context/ThemeContext.jsx";
import NotFoundRoute from "./routes/NotFoundRoute.jsx";
import ProtectedRoute from "./routes/auth/ProtectedRoute";
import SignInRoute from './routes/auth/SignInRoute';
import SignUpRoute from './routes/auth/SignUpRoute';
import TimeViewRoute from './routes/time/TimeViewRoute';
import ApprenticesRoute from './routes/apprentices/PersonsRoute.jsx';
import SubjectOverview from "@/routes/grademanager/SubjectOverview.jsx";
import SubjectDetails from "@/routes/grademanager/SubjectDetails.jsx";
import SchoolOverview from "@/routes/grademanager/SchoolOverview.jsx";
import AuthLayout from "@/layouts/AuthLayout.jsx";
import CoachDashboard from "@/routes/dashboard/CoachDashboard.jsx";
import ApprenticesDashboard from "@/routes/dashboard/ApprenticesDashboard.jsx";
import PersonProfile from "@/routes/apprentices/PersonProfile.jsx";
import EditPersonRoute from "@/routes/apprentices/EditPersonRoute.jsx";
import ActivationPage from "@/routes/apprentices/ActivationPage.jsx";
import CreateCoachRoute from "@/routes/coach/CreateCoachRoute.jsx";
import CreateAdminRoute from "@/routes/admin/CreateAdminRoute.jsx";
import AdminDashboardRoute from "@/routes/dashboard/AdminDashboardRoute.jsx";
import WelcomeRoute from "@/routes/dashboard/WelcomeRoute.jsx";
import ManageAccountsRoute from "@/routes/admin/ManageAccountsRoute.jsx";
import ManageTeamsRoute from "@/routes/team/ManageTeamsRoute.jsx";
import CoachOverview from "@/routes/coach/CoachOverview.jsx";
import EmergencyContactRoute from "@/routes/apprentices/EmergencyContactRoute.jsx";
import EditEmergencyContactRoute from "@/routes/apprentices/EditEmergencyContactRoute.jsx";

const router = createBrowserRouter([
    {
        element: (
            <ProtectedRoute>
                <ThemeProvider>
                    <MainLayout/>
                </ThemeProvider>
            </ProtectedRoute>
        ),
        children: [
            {path: "/", element: <WelcomeRoute/>},

            {
                path: "/apprentices",
                element: (
                    <ProtectedRoute allowedRoles={["COACH", "ADMIN"]}>
                        <ApprenticesRoute/>
                    </ProtectedRoute>
                ),
            },
            {
                path: "/timesheet",
                element: (
                    <ProtectedRoute>
                        <TimeViewRoute/>
                    </ProtectedRoute>
                ),
            },
            {
                path: "/schools",
                element: (
                    <ProtectedRoute>
                        <SchoolOverview/>
                    </ProtectedRoute>
                ),
            },
            {
                path: "/schools/:schoolId",
                element: (
                    <ProtectedRoute>
                        <SubjectOverview/>
                    </ProtectedRoute>
                ),
            },
            {
                path: "/schools/:schoolId/subjects/:subjectId",
                element: (
                    <ProtectedRoute>
                        <SubjectDetails/>
                    </ProtectedRoute>
                ),
            },
            {
                path: "/dashboard/coach",
                element: (
                    <ProtectedRoute allowedRoles={["COACH"]}>
                        <CoachDashboard/>
                    </ProtectedRoute>
                ),
            },
            {
                path: "/dashboard/apprentice/:personid",
                element: (
                    <ProtectedRoute>
                        <ApprenticesDashboard/>
                    </ProtectedRoute>
                ),
            },
            {
                path: "/dashboard/admin",
                element: (
                    <ProtectedRoute allowedRoles={["ADMIN"]}>
                        <AdminDashboardRoute/>
                    </ProtectedRoute>
                ),
            },
            {
                path: "/profile/:id",
                element: (
                    <ProtectedRoute allowedRoles={["COACH", "ADMIN"]}>
                        <PersonProfile/>
                    </ProtectedRoute>
                ),
            },
            {
                path: "/persons/edit/:id",
                element: (
                    <ProtectedRoute allowedRoles={["COACH", "ADMIN"]}>
                        <EditPersonRoute/>
                    </ProtectedRoute>
                ),
            },
            {
                path: "/apprentices/activation",
                element: (
                    <ProtectedRoute allowedRoles={["COACH", "ADMIN"]}>
                        <ActivationPage/>
                    </ProtectedRoute>
                ),
            },
            {
                path: "/admin/create-coach",
                element: (
                    <ProtectedRoute allowedRoles={["ADMIN"]}>
                        <CreateCoachRoute/>
                    </ProtectedRoute>
                ),
            },
            {
                path: "/admin/create-admin",
                element: (
                    <ProtectedRoute allowedRoles={["ADMIN"]}>
                        <CreateAdminRoute/>
                    </ProtectedRoute>
                ),
            },
            {
                path: "/admin/manage-accounts",
                element: (
                    <ProtectedRoute allowedRoles={["ADMIN"]}>
                        <ManageAccountsRoute/>
                    </ProtectedRoute>
                ),
            },
            {
                path: "*",
                element: <NotFoundRoute/>,
            },
            {
                path: "/teams",
                element: (
                    <ProtectedRoute allowedRoles={["COACH", "ADMIN"]}>
                        <ManageTeamsRoute/>
                    </ProtectedRoute>
                ),
            },
            {
                path: "/coach/overview",
                element: (
                    <ProtectedRoute allowedRoles={["COACH"]}>
                        <CoachOverview />
                    </ProtectedRoute>
                ),
            },
            {
                path: `/persons/:personId/emergency-contact`,
                element: (
                    <ProtectedRoute>
                        <EmergencyContactRoute />
                    </ProtectedRoute>
                )
            },
            {
                path: `/persons/:personId/edit-emergency-contact`,
                element: (
                    <ProtectedRoute>
                        <EditEmergencyContactRoute />
                    </ProtectedRoute>
                )
            }
        ],
    },
    {
        path: "/accounts/signin",
        element: (
            <AuthLayout>
                <SignInRoute/>
            </AuthLayout>
        ),
        action: SignInRoute.action,
    },
    {
        path: "/accounts/signup",
        element: (
            <AuthLayout>
                <SignUpRoute/>
            </AuthLayout>
        ),
        action: SignUpRoute.action,
    },

]);

export default router;
