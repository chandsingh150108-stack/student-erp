import React, { useEffect, useState } from 'react';
import { facultyApi, departmentsApi } from '../api/endpoints';
import { Faculty, Department } from '../api/types';
import { useAuth } from '../context/AuthContext';
import './Page.css';

const FacultyPage: React.FC = () => {
  const [faculty, setFaculty] = useState<Faculty[]>([]);
  const [departments, setDepartments] = useState<Department[]>([]);
  const [search, setSearch] = useState('');
  const [showForm, setShowForm] = useState(false);
  const [form, setForm] = useState<any>({ employeeNumber: '', firstName: '', lastName: '', email: '', phone: '', designation: '', specialization: '', employmentStatus: 'ACTIVE', department: { id: 0 } });
  const [editId, setEditId] = useState<number | null>(null);
  const { isAdmin } = useAuth();

  const load = () => { facultyApi.list().then(r => setFaculty(r.data)); departmentsApi.list().then(r => setDepartments(r.data)); };
  useEffect(() => { load(); }, []);

  const handleSearch = async () => {
    if (search) { const r = await facultyApi.search(search); setFaculty(r.data); }
    else { load(); }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (editId) { await facultyApi.update(editId, form); }
    else { await facultyApi.create(form); }
    setShowForm(false); setEditId(null); load();
  };

  return (
    <div className="page">
      <div className="page-header">
        <div><h1 className="page-title">Faculty</h1><p className="page-subtitle">{faculty.length} faculty members</p></div>
        {isAdmin && <button className="btn-primary" onClick={() => { setShowForm(true); setEditId(null); setForm({ employeeNumber: '', firstName: '', lastName: '', email: '', phone: '', designation: '', specialization: '', employmentStatus: 'ACTIVE', department: { id: departments[0]?.id || 0 } }); }}>+ Add Faculty</button>}
      </div>
      <div className="search-bar">
        <input placeholder="Search by name, employee number, or email..." value={search} onChange={e => setSearch(e.target.value)} onKeyDown={e => e.key === 'Enter' && handleSearch()} />
        <button className="btn-primary" onClick={handleSearch}>Search</button>
        {search && <button className="btn-secondary" onClick={() => { setSearch(''); load(); }}>Clear</button>}
      </div>
      {showForm && (
        <div className="form-card">
          <h3>{editId ? 'Edit' : 'Add'} Faculty</h3>
          <form onSubmit={handleSubmit}>
            <div className="form-row">
              <div className="form-group"><label>Employee Number</label><input value={form.employeeNumber} onChange={e => setForm({...form, employeeNumber: e.target.value})} required /></div>
              <div className="form-group"><label>Department</label><select value={form.department?.id || 0} onChange={e => setForm({...form, department: { id: Number(e.target.value) }})} required><option value="">Select</option>{departments.map(d => <option key={d.id} value={d.id}>{d.name}</option>)}</select></div>
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
              <div className="form-group"><label>Designation</label><input value={form.designation || ''} onChange={e => setForm({...form, designation: e.target.value})} /></div>
              <div className="form-group"><label>Specialization</label><input value={form.specialization || ''} onChange={e => setForm({...form, specialization: e.target.value})} /></div>
            </div>
            <div className="form-actions"><button type="button" className="btn-secondary" onClick={() => setShowForm(false)}>Cancel</button><button type="submit" className="btn-primary">{editId ? 'Update' : 'Create'}</button></div>
          </form>
        </div>
      )}
      <div className="table-card">
        <table>
          <thead><tr><th>Emp No</th><th>Name</th><th>Department</th><th>Designation</th><th>Email</th><th>Status</th>{isAdmin && <th>Actions</th>}</tr></thead>
          <tbody>
            {faculty.map(f => (
              <tr key={f.id}>
                <td><span className="badge">{f.employeeNumber}</span></td>
                <td><strong>{f.firstName} {f.lastName}</strong></td>
                <td>{f.department?.name}</td>
                <td>{f.designation}</td>
                <td>{f.email}</td>
                <td><span className={`status ${f.employmentStatus === 'ACTIVE' ? 'active' : 'inactive'}`}>{f.employmentStatus}</span></td>
                {isAdmin && <td><button className="btn-sm" onClick={() => { setForm(f); setEditId(f.id!); setShowForm(true); }}>Edit</button><button className="btn-sm danger" onClick={() => { if(confirm('Delete?')) { facultyApi.delete(f.id!).then(load); } }}>Delete</button></td>}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default FacultyPage;
