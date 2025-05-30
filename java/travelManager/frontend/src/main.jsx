localStorage.removeItem('theme');
document.documentElement.setAttribute('data-bs-theme', 'light');

import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { RouterProvider } from 'react-router-dom';
import { MessageProvider } from '@/context/MessageContext';
import router from './router.jsx';
import './index.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import './theme.css';

createRoot(document.getElementById('root')).render(
    <StrictMode>
        <MessageProvider>
            <RouterProvider router={router}/>
        </MessageProvider>
    </StrictMode>
);
