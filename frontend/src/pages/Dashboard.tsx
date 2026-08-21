import React, { useEffect, useState } from 'react';
import { dashboardApi } from '../api/endpoints';
import { DashboardStats } from '../api/types';
import './Dashboard.css';

const Dashboard: React.FC = () => {
  const [stats, setStats] = useState<DashboardStats | null>(null);
  useEffect(() => { dashboardApi.getStats().then(r => setStats(r.data)); }, []);

  if (!stats) return <div className="loading">Loading...</div>;

  const cards = [
    { label: 'Total Students', value: stats.totalStudents, color: '#1a73e8', icon: '🎓' },
    { label: 'Total Faculty', value: stats.totalFaculty, color: '#34a853', icon: '👨‍🏫' },
    { label: 'Departments', value: stats.totalDepartments, color: '#ea4335', icon: '🏛' },
    { label: 'Programs', value: stats.totalPrograms, color: '#fbbc04', icon: '📋' },
    { label: 'Active Courses', value: stats.activeCourses, color: '#9334e6', icon: '📚' },
    { label: 'Pending Fees', value: stats.pendingFees, color: '#ff6d01', icon: '💰' },
    { label: 'Open Complaints', value: stats.openComplaints, color: '#d93025', icon: '📝' },
    { label: 'Upcoming Events', value: stats.upcomingEvents, color: '#185abc', icon: '🎉' },
    { label: 'Library Books', value: stats.totalBooks, color: '#0d652d', icon: '📖' },
    { label: 'Issued Books', value: stats.issuedBooks, color: '#c5221f', icon: '📤' },
  ];

  return (
    <div className="dashboard">
      <h1 className="page-title">Dashboard</h1>
      <p className="page-subtitle">Welcome to Student ERP System</p>
      <div className="stats-grid">
        {cards.map(card => (
          <div key={card.label} className="stat-card">
            <div className="stat-icon" style={{ background: card.color }}>{card.icon}</div>
            <div className="stat-info">
              <span className="stat-value">{card.value}</span>
              <span className="stat-label">{card.label}</span>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Dashboard;
