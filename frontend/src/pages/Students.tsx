import React, { useEffect, useState } from 'react';
import { studentsApi, programsApi } from '../api/endpoints';
import { Student, Program } from '../api/types';
import { useAuth } from '../context/AuthContext';
import './Page.css';

const Students: React.FC = () => {
  const [students, setStudents] = useState<Student[]>([]);
  const [programs, setPrograms] = useState<Program[]>([]);
  const [search, setSearch] = useState('');
  const [showForm, setShowForm] = useState(false);
  const [form, setForm] = useState<any>({ rollNumber: '', firstName: '', lastName: '', email: '', phone: '', gender: 'Male', status: 'ACTIVE', currentSemester: 1, program: { id: 0 } });
  const [editId, setEditId] = useState<number | null>(null);
  const { isAdmin } = useAuth();

  const load = () => { studentsApi.list().then(r => setStudents(r.data)); programsApi.list().then(r => setPrograms(r.data)); };
  useEffect(() => { load(); }, []);

  const handleSearch = async () => {
    if (search) { const r = await studentsApi.search(search); setStudents(r.data); }
    else { load(); }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (editId) { await studentsApi.update(editId, form); }
    else { await studentsApi.create(form); }
    setShowForm(false); setEditId(null); load();
  };

  return (
    <div className="page">
      <div className="page-header">
        <div><h1 className="page-title">Students</h1><p className="page-subtitle">{students.length} students</p></div>
        {isAdmin && <button className="btn-primary" onClick={() => { setShowForm(true); setEditId(null); setForm({ rollNumber: '', firstName: '', lastName: '', email: '', phone: '', gender: 'Male', status: 'ACTIVE', currentSemester: 1, program: { id: programs[0]?.id || 0 } }); }}>+ Add Student</button>}
      </div>
      <div className="search-bar">
        <input placeholder="Search by name, roll number, or email..." value={search} onChange={e => setSearch(e.target.value)} onKeyDown={e => e.key === 'Enter' && handleSearch()} />
        <button className="btn-primary" onClick={handleSearch}>Search</button>
        {search && <button className="btn-secondary" onClick={() => { setSearch(''); load(); }}>Clear</button>}
      </div>
      {showForm && (
        <div className="form-card">
          <h3>{editId ? 'Edit' : 'Add'} Student</h3>
          <form onSubmit={handleSubmit}>
            <div className="form-row">
              <div className="form-group"><label>Roll Number</label><input value={form.rollNumber} onChange={e => setForm({...form, rollNumber: e.target.value})} required /></div>
              <div className="form-group"><label>Program</label><select value={form.program?.id || 0} onChange={e => setForm({...form, program: { id: Number(e.target.value) }})} required><option value="">Select</option>{programs.map(p => <option key={p.id} value={p.id}>{p.name}</option>)}</select></div>
            </div>
            <div className="form-row">
              <div className="form-group"><label>First Name</label><input value={form.firstName} onChange={e => setForm({...form, firstName: e.target.value})} required /></div>
              <div className="form-group"><label>Last Name</label><input value={form.lastName} onChange={e => setForm({...form, lastName: e.target.value})} required /></div>
            </div>
            <div className="form-row">
              <div className="form-group"><label>Email</label><input type="email" value={form.email || ''} onChange={e => setForm({...form, email: e.target.value})} /></div>
              <div className="form-group"><label>Phone</label><input value={form.phone || ''} onChange={e => setForm({...form, phone: e.target.value})} /></div>
            </div>
            <div className="form-row">
              <div className="form-group"><label>Gender</label><select value={form.gender || 'Male'} onChange={e => setForm({...form, gender: e.target.value})}><option>Male</option><option>Female</option><option>Other</option></select></div>
              <div className="form-group"><label>Semester</label><input type="number" value={form.currentSemester || 1} onChange={e => setForm({...form, currentSemester: Number(e.target.value)})} /></div>
            </div>
            <div className="form-actions"><button type="button" className="btn-secondary" onClick={() => setShowForm(false)}>Cancel</button><button type="submit" className="btn-primary">{editId ? 'Update' : 'Create'}</button></div>
          </form>
        </div>
      )}
      <div className="table-card">
        <table>
          <thead><tr><th>Roll No</th><th>Name</th><th>Program</th><th>Semester</th><th>Email</th><th>Status</th>{isAdmin && <th>Actions</th>}</tr></thead>
          <tbody>
            {students.map(s => (
              <tr key={s.id}>
                <td><span className="badge">{s.rollNumber}</span></td>
                <td><strong>{s.firstName} {s.lastName}</strong></td>
                <td>{s.program?.name}</td>
                <td>{s.currentSemester}</td>
                <td>{s.email}</td>
                <td><span className={`status ${s.status === 'ACTIVE' ? 'active' : 'inactive'}`}>{s.status}</span></td>
                {isAdmin && <td><button className="btn-sm" onClick={() => { setForm(s); setEditId(s.id!); setShowForm(true); }}>Edit</button><button className="btn-sm danger" onClick={() => { if(confirm('Delete?')) { studentsApi.delete(s.id!).then(load); } }}>Delete</button></td>}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Students;
