import React, { useEffect, useState } from 'react';
import { complaintsApi } from '../api/endpoints';
import { Complaint } from '../api/types';
import { useAuth } from '../context/AuthContext';
import './Page.css';

const Complaints: React.FC = () => {
  const [complaints, setComplaints] = useState<Complaint[]>([]);
  const [showForm, setShowForm] = useState(false);
  const [form, setForm] = useState<any>({ subject: '', description: '' });
  const { isAdmin } = useAuth();
  const load = () => complaintsApi.list().then(r => setComplaints(r.data));
  useEffect(() => { load(); }, []);
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    await complaintsApi.create(form);
    setShowForm(false); load();
  };
  const statusColors: Record<string, string> = { OPEN: '#ea4335', IN_PROGRESS: '#fbbc04', RESOLVED: '#34a853', CLOSED: '#888' };
  return (
    <div className="page">
      <div className="page-header">
        <div><h1 className="page-title">Complaints</h1><p className="page-subtitle">{complaints.length} complaints</p></div>
        <button className="btn-primary" onClick={() => setShowForm(true)}>+ Submit Complaint</button>
      </div>
      {showForm && (
        <div className="form-card">
          <form onSubmit={handleSubmit}>
            <div className="form-group"><label>Subject</label><input value={form.subject} onChange={e => setForm({...form, subject: e.target.value})} required /></div>
            <div className="form-group"><label>Description</label><textarea rows={4} value={form.description} onChange={e => setForm({...form, description: e.target.value})} required /></div>
            <div className="form-actions"><button type="button" className="btn-secondary" onClick={() => setShowForm(false)}>Cancel</button><button type="submit" className="btn-primary">Submit</button></div>
          </form>
        </div>
      )}
      <div className="table-card">
        <table>
          <thead><tr><th>ID</th><th>Subject</th><th>Description</th><th>Status</th><th>Date</th></tr></thead>
          <tbody>
            {complaints.map(c => (
              <tr key={c.id}>
                <td>#{c.id}</td>
                <td><strong>{c.subject}</strong></td>
                <td>{c.description?.substring(0, 80)}...</td>
                <td><span style={{background: statusColors[c.status] || '#888', color: 'white', padding: '2px 10px', borderRadius: 12, fontSize: 12}}>{c.status}</span></td>
                <td>{c.submissionDate ? new Date(c.submissionDate).toLocaleDateString() : ''}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};
export default Complaints;
