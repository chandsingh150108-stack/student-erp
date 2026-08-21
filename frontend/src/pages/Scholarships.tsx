import React, { useEffect, useState } from 'react';
import { scholarshipsApi } from '../api/endpoints';
import { Scholarship } from '../api/types';
import { useAuth } from '../context/AuthContext';
import './Page.css';

const Scholarships: React.FC = () => {
  const [scholarships, setScholarships] = useState<Scholarship[]>([]);
  const [showForm, setShowForm] = useState(false);
  const [form, setForm] = useState<any>({ name: '', description: '', amount: 0, eligibilityInfo: '', active: true });
  const { isAdmin } = useAuth();
  const load = () => scholarshipsApi.list().then(r => setScholarships(r.data));
  useEffect(() => { load(); }, []);
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    await scholarshipsApi.create(form);
    setShowForm(false); load();
  };
  return (
    <div className="page">
      <div className="page-header">
        <div><h1 className="page-title">Scholarships</h1><p className="page-subtitle">{scholarships.length} scholarships</p></div>
        {isAdmin && <button className="btn-primary" onClick={() => { setShowForm(true); setForm({ name: '', description: '', amount: 0, eligibilityInfo: '', active: true }); }}>+ Add Scholarship</button>}
      </div>
      {showForm && (
        <div className="form-card">
          <form onSubmit={handleSubmit}>
            <div className="form-row">
              <div className="form-group"><label>Name</label><input value={form.name} onChange={e => setForm({...form, name: e.target.value})} required /></div>
              <div className="form-group"><label>Amount</label><input type="number" value={form.amount} onChange={e => setForm({...form, amount: Number(e.target.value)})} required /></div>
            </div>
            <div className="form-group"><label>Description</label><input value={form.description || ''} onChange={e => setForm({...form, description: e.target.value})} /></div>
            <div className="form-group"><label>Eligibility</label><input value={form.eligibilityInfo || ''} onChange={e => setForm({...form, eligibilityInfo: e.target.value})} /></div>
            <div className="form-actions"><button type="button" className="btn-secondary" onClick={() => setShowForm(false)}>Cancel</button><button type="submit" className="btn-primary">Create</button></div>
          </form>
        </div>
      )}
      <div className="card-list">
        {scholarships.map(s => (
          <div key={s.id} className="list-card">
            <h3>{s.name}</h3>
            <p className="meta">Amount: ₹{s.amount.toLocaleString()}</p>
            <p>{s.description}</p>
            {s.eligibilityInfo && <p><strong>Eligibility:</strong> {s.eligibilityInfo}</p>}
            {isAdmin && <button className="btn-sm danger" style={{marginTop: 8}} onClick={() => { if(confirm('Delete?')) { scholarshipsApi.delete(s.id!).then(load); } }}>Delete</button>}
          </div>
        ))}
      </div>
    </div>
  );
};
export default Scholarships;
