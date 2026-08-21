import React, { useEffect, useState } from 'react';
import { announcementsApi } from '../api/endpoints';
import { Announcement } from '../api/types';
import { useAuth } from '../context/AuthContext';
import './Page.css';

const Announcements: React.FC = () => {
  const [announcements, setAnnouncements] = useState<Announcement[]>([]);
  const [showForm, setShowForm] = useState(false);
  const [form, setForm] = useState<Announcement>({ title: '', content: '', author: '' });
  const { isAdmin, isFaculty } = useAuth();
  const load = () => announcementsApi.list().then(r => setAnnouncements(r.data));
  useEffect(() => { load(); }, []);
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    await announcementsApi.create(form);
    setShowForm(false); setForm({ title: '', content: '', author: '' }); load();
  };
  return (
    <div className="page">
      <div className="page-header">
        <div><h1 className="page-title">Announcements</h1><p className="page-subtitle">{announcements.length} announcements</p></div>
        {(isAdmin || isFaculty) && <button className="btn-primary" onClick={() => setShowForm(true)}>+ New Announcement</button>}
      </div>
      {showForm && (
        <div className="form-card">
          <h3>Create Announcement</h3>
          <form onSubmit={handleSubmit}>
            <div className="form-group"><label>Title</label><input value={form.title} onChange={e => setForm({...form, title: e.target.value})} required /></div>
            <div className="form-group"><label>Content</label><textarea rows={4} value={form.content} onChange={e => setForm({...form, content: e.target.value})} required /></div>
            <div className="form-group"><label>Author</label><input value={form.author || ''} onChange={e => setForm({...form, author: e.target.value})} /></div>
            <div className="form-actions"><button type="button" className="btn-secondary" onClick={() => setShowForm(false)}>Cancel</button><button type="submit" className="btn-primary">Publish</button></div>
          </form>
        </div>
      )}
      <div className="card-list">
        {announcements.map(a => (
          <div key={a.id} className="list-card">
            <h3>{a.title}</h3>
            <p className="meta">{a.author && `By ${a.author}`} {a.createdDate && `• ${new Date(a.createdDate).toLocaleDateString()}`}</p>
            <p>{a.content}</p>
            {isAdmin && <button className="btn-sm danger" style={{marginTop: 8}} onClick={() => { if(confirm('Delete?')) { announcementsApi.delete(a.id!).then(load); } }}>Delete</button>}
          </div>
        ))}
      </div>
    </div>
  );
};
export default Announcements;
