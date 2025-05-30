import {redirect, useActionData, useNavigate} from "react-router-dom";
import AuthForm from "@/components/auth/AuthForm.jsx";
import {signIn} from "@/lib/auth/auth";
import {saveSession} from "@/lib/session";
import {validateUser} from "@/lib/auth/validateUser";

async function clientAction({request}) {
    const formData = await request.formData();
    const user = Object.fromEntries(formData);

    const {errors, isValid} = validateUser(user);
    if (!isValid) {
        return errors;
    }

    try {
        const response = await signIn(user);
        if (!response) {
            return {
                email: "E-Mail oder Passwort ist falsch.",
                password: "",
            };
        }

        saveSession(response);

        const roles = response?.account?.roles || [];
        const id = response?.account?.id;

        if (roles.includes("ADMIN")) {
            return redirect("/dashboard/admin");
        }
        if (roles.includes("COACH")) {
            return redirect("/dashboard/coach");
        }
        if (roles.includes("APPRENTICE") && id) {
            return redirect(`/dashboard/apprentice/${id}`);
        }

        return redirect("/");

        // eslint-disable-next-line no-unused-vars
    } catch (err) {
        return {
            email: "Unbekannter Fehler beim Einloggen.",
            password: "",
        };
    }
}

export default function SignInRoute() {
    const errors = useActionData();
    const navigate = useNavigate();

    const goBack = () => {
        navigate("/");
    };

    return (
        <main>
            <AuthForm onCancel={goBack} errors={errors}/>
        </main>
    );
}

SignInRoute.action = clientAction;
