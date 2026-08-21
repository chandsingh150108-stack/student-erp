import React, { useEffect, useState } from 'react';
import { programsApi, departmentsApi } from '../api/endpoints';
import { Program, Department } from '../api/types';
import { useAuth } from '../context/AuthContext';
import './Page.css';

const Programs: React.FC = () => {
  const [programs, setPrograms] = useState<Program[]>([]);
  const [departments, setDepartments] = useState<Department[]>([]);
  const [showForm, setShowForm] = useState(false);
  const [form, setForm] = useState<any>({ code: '', name: '', degreeType: 'B.Tech', durationYears: 4, totalCredits: 160, active: true, department: { id: 0 } });
  const [editId, setEditId] = useState<number | null>(null);
  const { isAdmin } = useAuth();

  const load = () => { programsApi.list().then(r => setPrograms(r.data)); departmentsApi.list().then(r => setDepartments(r.data)); };
  useEffect(() => { load(); }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (editId) { await programsApi.update(editId, form); }
    else { await programsApi.create(form); }
    setShowForm(false); setEditId(null); load();
  };

  return (
    <div className="page">
      <div className="page-header">
        <div><h1 className="page-title">Programs</h1><p className="page-subtitle">{programs.length} programs</p></div>
        {isAdmin && <button className="btn-primary" onClick={() => { setShowForm(true); setEditId(null); setForm({ code: '', name: '', degreeType: 'B.Tech', durationYears: 4, totalCredits: 160, active: true, department: { id: departments[0]?.id || 0 } }); }}>+ Add Program</button>}
      </div>
      {showForm && (
        <div className="form-card">
          <h3>{editId ? 'Edit' : 'Add'} Program</h3>
          <form onSubmit={handleSubmit}>
            <div className="form-row">
              <div className="form-group"><label>Code</label><input value={form.code} onChange={e => setForm({...form, code: e.target.value})} required /></div>
              <div className="form-group"><label>Name</label><input value={form.name} onChange={e => setForm({...form, name: e.target.value})} required /></div>
            </div>
            <div className="form-row">
              <div className="form-group"><label>Department</label><select value={form.department.id} onChange={e => setForm({...form, department: { id: Number(e.target.value) }})} required><option value="">Select</option>{departments.map(d => <option key={d.id} value={d.id}>{d.name}</option>)}</select></div>
              <div className="form-group"><label>Degree Type</label><input value={form.degreeType} onChange={e => setForm({...form, degreeType: e.target.value})} required /></div>
            </div>
            <div className="form-row">
              <div className="form-group"><label>Duration (Years)</label><input type="number" value={form.durationYears} onChange={e => setForm({...form, durationYears: Number(e.target.value)})} required /></div>
              <div className="form-group"><label>Total Credits</label><input type="number" value={form.totalCredits} onChange={e => setForm({...form, totalCredits: Number(e.target.value)})} required /></div>
            </div>
            <div className="form-actions"><button type="button" className="btn-secondary" onClick={() => setShowForm(false)}>Cancel</button><button type="submit" className="btn-primary">{editId ? 'Update' : 'Create'}</button></div>
          </form>
        </div>
      )}
      <div className="table-card">
        <table>
          <thead><tr><th>Code</th><th>Name</th><th>Department</th><th>Degree</th><th>Duration</th><th>Credits</th><th>Status</th>{isAdmin && <th>Actions</th>}</tr></thead>
          <tbody>
            {programs.map(p => (
              <tr key={p.id}>
                <td><span className="badge">{p.code}</span></td>
                <td><strong>{p.name}</strong></td>
                <td>{p.department?.name}</td>
                <td>{p.degreeType}</td>
                <td>{p.durationYears} years</td>
                <td>{p.totalCredits}</td>
                <td><span className={`status ${p.active ? 'active' : 'inactive'}`}>{p.active ? 'Active' : 'Inactive'}</span></td>
                {isAdmin && <td><button className="btn-sm" onClick={() => { setForm(p); setEditId(p.id!); setShowForm(true); }}>Edit</button><button className="btn-sm danger" onClick={() => { if(confirm('Delete?')) { programsApi.delete(p.id!).then(load); } }}>Delete</button></td>}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Programs;
