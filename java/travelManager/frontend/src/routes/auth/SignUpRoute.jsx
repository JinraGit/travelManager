import {redirect, useActionData, useNavigate} from "react-router-dom"

import {signUp} from "@/lib/auth/auth"
import {saveSession} from "@/lib/session"
import {validateRegistration} from "@/lib/auth/validateRegistration"
import RegistrationForm from "@/components/auth/RegistrationForm.jsx"

async function clientAction({request}) {
    const formData = await request.formData()
    const user = Object.fromEntries(formData)

    const {errors, isValid} = validateRegistration(user)
    if (!isValid) {
        return errors
    }

    const response = await signUp(user)
    saveSession(response)
    return redirect("/auth/signin")
}

export default function SignUpRoute() {
    const errors = useActionData()
    const navigate = useNavigate()

    const goBack = () => {
        navigate("/")
    }

    return (
        <main>
            <RegistrationForm onCancel={goBack} errors={errors}/>
        </main>
    )
}

SignUpRoute.action = clientAction
