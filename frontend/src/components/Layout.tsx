import React from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import './Layout.css';

const Layout: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const { user, logout, isAdmin, isFaculty, isStudent } = useAuth();
  const location = useLocation();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  const navItems = [
    { path: '/', label: 'Dashboard', icon: '📊', roles: ['ADMIN', 'FACULTY', 'STUDENT'] },
    { path: '/students', label: 'Students', icon: '🎓', roles: ['ADMIN', 'FACULTY'] },
    { path: '/faculty', label: 'Faculty', icon: '👨‍🏫', roles: ['ADMIN'] },
    { path: '/departments', label: 'Departments', icon: '🏛', roles: ['ADMIN'] },
    { path: '/programs', label: 'Programs', icon: '📋', roles: ['ADMIN'] },
    { path: '/courses', label: 'Courses', icon: '📚', roles: ['ADMIN', 'FACULTY'] },
    { path: '/sections', label: 'Sections', icon: '👥', roles: ['ADMIN'] },
    { path: '/announcements', label: 'Announcements', icon: '📢', roles: ['ADMIN', 'FACULTY', 'STUDENT'] },
    { path: '/events', label: 'Events', icon: '🎉', roles: ['ADMIN', 'FACULTY', 'STUDENT'] },
    { path: '/scholarships', label: 'Scholarships', icon: '🏆', roles: ['ADMIN', 'FACULTY', 'STUDENT'] },
    { path: '/complaints', label: 'Complaints', icon: '📝', roles: ['ADMIN', 'FACULTY', 'STUDENT'] },
    { path: '/backlogs', label: 'Backlogs', icon: '⚠️', roles: ['ADMIN', 'STUDENT'] },
    { path: '/library', label: 'Library', icon: '📖', roles: ['ADMIN', 'FACULTY', 'STUDENT'] },
  ];

  const roleLabel = user?.role === 'ADMIN' ? 'Administrator' : user?.role === 'FACULTY' ? 'Faculty' : 'Student';

  return (
    <div className="layout">
      <aside className="sidebar">
        <div className="sidebar-header">
          <h2>🎓 Student ERP</h2>
        </div>
        <nav className="sidebar-nav">
          {navItems.filter(item => item.roles.includes(user?.role || '')).map(item => (
            <Link
              key={item.path}
              to={item.path}
              className={`nav-item ${location.pathname === item.path ? 'active' : ''}`}
            >
              <span className="nav-icon">{item.icon}</span>
              <span className="nav-label">{item.label}</span>
            </Link>
          ))}
        </nav>
        <div className="sidebar-footer">
          <div className="user-info">
            <div className="user-avatar">{user?.username?.charAt(0).toUpperCase()}</div>
            <div className="user-details">
              <span className="user-name">{user?.username}</span>
              <span className="user-role">{roleLabel}</span>
            </div>
          </div>
          <button onClick={handleLogout} className="logout-btn">Sign Out</button>
        </div>
      </aside>
      <main className="main-content">
        {children}
      </main>
    </div>
  );
};

export default Layout;
