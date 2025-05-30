export default function AuthLayout({children}) {
    return (
        <div data-bs-theme="light" style={{minHeight: "100vh"}}>
            {children}
        </div>
    );
}