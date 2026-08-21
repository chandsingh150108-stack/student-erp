import React, { useEffect, useState } from 'react';
import { coursesApi, departmentsApi } from '../api/endpoints';
import { Course, Department } from '../api/types';
import { useAuth } from '../context/AuthContext';
import './Page.css';

const Courses: React.FC = () => {
  const [courses, setCourses] = useState<Course[]>([]);
  const [departments, setDepartments] = useState<Department[]>([]);
  const [showForm, setShowForm] = useState(false);
  const [form, setForm] = useState<any>({ code: '', name: '', description: '', credits: 3, courseType: 'CORE', lectureHours: 3, department: { id: 0 } });
  const [editId, setEditId] = useState<number | null>(null);
  const { isAdmin } = useAuth();
  const load = () => { coursesApi.list().then(r => setCourses(r.data)); departmentsApi.list().then(r => setDepartments(r.data)); };
  useEffect(() => { load(); }, []);
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (editId) { await coursesApi.update(editId, form); } else { await coursesApi.create(form); }
    setShowForm(false); setEditId(null); load();
  };
  return (
    <div className="page">
      <div className="page-header">
        <div><h1 className="page-title">Courses</h1><p className="page-subtitle">{courses.length} courses</p></div>
        {isAdmin && <button className="btn-primary" onClick={() => { setShowForm(true); setEditId(null); setForm({ code: '', name: '', description: '', credits: 3, courseType: 'CORE', department: { id: departments[0]?.id || 0 } }); }}>+ Add Course</button>}
      </div>
      {showForm && (
        <div className="form-card">
          <h3>{editId ? 'Edit' : 'Add'} Course</h3>
          <form onSubmit={handleSubmit}>
            <div className="form-row">
              <div className="form-group"><label>Code</label><input value={form.code} onChange={e => setForm({...form, code: e.target.value})} required /></div>
              <div className="form-group"><label>Name</label><input value={form.name} onChange={e => setForm({...form, name: e.target.value})} required /></div>
            </div>
            <div className="form-row">
              <div className="form-group"><label>Department</label><select value={form.department.id} onChange={e => setForm({...form, department: { id: Number(e.target.value) }})} required><option value="">Select</option>{departments.map(d => <option key={d.id} value={d.id}>{d.name}</option>)}</select></div>
              <div className="form-group"><label>Credits</label><input type="number" value={form.credits} onChange={e => setForm({...form, credits: Number(e.target.value)})} required /></div>
            </div>
            <div className="form-row">
              <div className="form-group"><label>Type</label><select value={form.courseType} onChange={e => setForm({...form, courseType: e.target.value})}><option>CORE</option><option>ELECTIVE</option><option>LAB</option><option>PROJECT</option></select></div>
              <div className="form-group"><label>Lecture Hours</label><input type="number" value={form.lectureHours || 0} onChange={e => setForm({...form, lectureHours: Number(e.target.value)})} /></div>
            </div>
            <div className="form-group"><label>Description</label><input value={form.description || ''} onChange={e => setForm({...form, description: e.target.value})} /></div>
            <div className="form-actions"><button type="button" className="btn-secondary" onClick={() => setShowForm(false)}>Cancel</button><button type="submit" className="btn-primary">{editId ? 'Update' : 'Create'}</button></div>
          </form>
        </div>
      )}
      <div className="table-card">
        <table>
          <thead><tr><th>Code</th><th>Name</th><th>Department</th><th>Credits</th><th>Type</th>{isAdmin && <th>Actions</th>}</tr></thead>
          <tbody>
            {courses.map(c => (
              <tr key={c.id}>
                <td><span className="badge">{c.code}</span></td>
                <td><strong>{c.name}</strong></td>
                <td>{c.department?.name}</td>
                <td>{c.credits}</td>
                <td><span className="badge">{c.courseType}</span></td>
                {isAdmin && <td><button className="btn-sm" onClick={() => { setForm(c); setEditId(c.id!); setShowForm(true); }}>Edit</button><button className="btn-sm danger" onClick={() => { if(confirm('Delete?')) { coursesApi.delete(c.id!).then(load); } }}>Delete</button></td>}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};
export default Courses;
