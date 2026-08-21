import React, { useEffect, useState } from 'react';
import { backlogsApi } from '../api/endpoints';
import { Backlog } from '../api/types';
import './Page.css';

const Backlogs: React.FC = () => {
  const [backlogs, setBacklogs] = useState<Backlog[]>([]);
  useEffect(() => { backlogsApi.byStudent(1).then(r => setBacklogs(r.data)).catch(() => {}); }, []);
  return (
    <div className="page">
      <h1 className="page-title">Backlogs</h1>
      <p className="page-subtitle">{backlogs.length} backlog records</p>
      {backlogs.length === 0 ? <div className="empty-state">No backlog records found.</div> : (
        <div className="table-card">
          <table>
            <thead><tr><th>Course</th><th>Semester</th><th>Status</th><th>Attempt</th></tr></thead>
            <tbody>
              {backlogs.map(b => (
                <tr key={b.id}>
                  <td>{b.course?.name}</td>
                  <td>Semester {b.semester?.semesterNumber}</td>
                  <td><span className={`status ${b.status === 'ACTIVE' ? 'inactive' : 'active'}`}>{b.status}</span></td>
                  <td>{b.attemptNumber}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};
export default Backlogs;
