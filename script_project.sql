--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.14
-- Dumped by pg_dump version 11.5

-- Started on 2020-11-01 20:57:23

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2 (class 3079 OID 107992)
-- Name: uuid-ossp; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;


--
-- TOC entry 2238 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION "uuid-ossp"; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 186 (class 1259 OID 108003)
-- Name: adm_function; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.adm_function (
    id bigint NOT NULL,
    code character varying(50) NOT NULL,
    libelle character varying(255),
    actif_fonc boolean DEFAULT false,
    date_creation timestamp without time zone,
    date_update timestamp without time zone,
    icon character varying(255),
    id_parent bigint,
    router character varying(255),
    flg_admin bigint
);


ALTER TABLE public.adm_function OWNER TO postgres;

--
-- TOC entry 187 (class 1259 OID 108009)
-- Name: adm_profile; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.adm_profile (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    code character varying(50) NOT NULL,
    role character varying(50) NOT NULL,
    libelle character varying(255),
    date_creation timestamp without time zone,
    date_update timestamp without time zone
);


ALTER TABLE public.adm_profile OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 108026)
-- Name: adm_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.adm_user (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    login character varying(50) NOT NULL,
    password character varying NOT NULL,
    date_creation timestamp without time zone,
    date_update timestamp without time zone,
    flg_actif smallint DEFAULT 0 NOT NULL,
    id_adm_profile uuid NOT NULL,
    id_personnel uuid NOT NULL,
    date_expired timestamp without time zone NOT NULL,
    ip_address character varying(255),
    flg_delete boolean DEFAULT false
);


ALTER TABLE public.adm_user OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 116076)
-- Name: adm_user_historique; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.adm_user_historique (
    id bigint NOT NULL,
    date_update timestamp without time zone,
    flg_change_login boolean,
    flg_valid boolean,
    id_adm_user uuid NOT NULL,
    old_login character varying(255)
);


ALTER TABLE public.adm_user_historique OWNER TO postgres;

--
-- TOC entry 190 (class 1259 OID 108048)
-- Name: function_profile; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.function_profile (
    id bigint NOT NULL,
    id_adm_profile uuid NOT NULL,
    id_adm_function bigint NOT NULL
);


ALTER TABLE public.function_profile OWNER TO postgres;

--
-- TOC entry 191 (class 1259 OID 108063)
-- Name: log_access; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.log_access (
    id bigint NOT NULL,
    code_access character varying(50) NOT NULL,
    date_auth timestamp without time zone,
    login character varying(255) NOT NULL,
    id_adm_user uuid,
    ip_address character varying(255)
);


ALTER TABLE public.log_access OWNER TO postgres;

--
-- TOC entry 192 (class 1259 OID 108068)
-- Name: log_data; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.log_data (
    id bigint NOT NULL,
    date_log timestamp without time zone NOT NULL,
    ip_address character varying(255) NOT NULL,
    id_adm_user uuid NOT NULL,
    http_method character varying(50) NOT NULL,
    uri character varying(255) NOT NULL,
    result_ws character varying(255) NOT NULL
);


ALTER TABLE public.log_data OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 108017)
-- Name: personnel; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.personnel (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    first_name character varying(100) NOT NULL,
    last_name character varying(100) NOT NULL,
    num_phone character varying,
    date_creation timestamp without time zone,
    date_update timestamp without time zone
);


ALTER TABLE public.personnel OWNER TO postgres;

--
-- TOC entry 193 (class 1259 OID 108076)
-- Name: seq_adm_function; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_adm_function
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_adm_function OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 116081)
-- Name: seq_adm_user_historique; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_adm_user_historique
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_adm_user_historique OWNER TO postgres;

--
-- TOC entry 194 (class 1259 OID 108078)
-- Name: seq_function_profile; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_function_profile
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_function_profile OWNER TO postgres;

--
-- TOC entry 195 (class 1259 OID 108080)
-- Name: seq_log_access; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_log_access
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_log_access OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 108082)
-- Name: seq_log_data; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_log_data
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_log_data OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 112713)
-- Name: v_adm_user; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_adm_user AS
 SELECT au.id,
    au.login,
    au.password,
    au.flg_actif,
    ap.id AS id_adm_profile,
    ap.libelle AS libelle_adm_profile,
    ap.role,
    p.id AS id_personnel,
    p.first_name,
    p.last_name,
    au.date_creation,
    au.date_update,
    au.date_expired
   FROM ((public.adm_user au
     JOIN public.adm_profile ap ON ((ap.id = au.id_adm_profile)))
     JOIN public.personnel p ON ((p.id = au.id_personnel)));


ALTER TABLE public.v_adm_user OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 116065)
-- Name: v_log_access; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_log_access AS
 SELECT la.id,
    la.date_auth,
    la.code_access,
        CASE
            WHEN ((la.code_access)::text = '200'::text) THEN 'Auth with succes'::text
            WHEN ((la.code_access)::text = '201'::text) THEN 'Account no actif'::text
            WHEN ((la.code_access)::text = '202'::text) THEN 'compte exprimer'::text
            ELSE 'echec'::text
        END AS libelle_log_access,
    la.id_adm_user,
    la.login,
    ap.id AS id_adm_profile,
    ap.libelle AS libelle_adm_profile,
    ap.role,
    p.id AS id_personnel,
    p.first_name,
    p.last_name,
    la.ip_address
   FROM (((public.log_access la
     LEFT JOIN public.adm_user au ON ((au.id = la.id_adm_user)))
     LEFT JOIN public.adm_profile ap ON ((ap.id = au.id_adm_profile)))
     LEFT JOIN public.personnel p ON ((p.id = au.id_personnel)));


ALTER TABLE public.v_log_access OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 116071)
-- Name: v_log_data; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_log_data AS
 SELECT ld.id,
    ld.date_log,
    ld.http_method,
    ld.ip_address,
    ld.result_ws,
    ld.uri,
    ld.id_adm_user,
    ap.id AS id_adm_profile,
    ap.libelle AS libelle_adm_profile,
    ap.role,
    p.id AS id_personnel,
    p.first_name,
    p.last_name
   FROM (((public.log_data ld
     JOIN public.adm_user au ON ((au.id = ld.id_adm_user)))
     JOIN public.adm_profile ap ON ((ap.id = au.id_adm_profile)))
     JOIN public.personnel p ON ((p.id = au.id_personnel)));


ALTER TABLE public.v_log_data OWNER TO postgres;

--
-- TOC entry 2220 (class 0 OID 108003)
-- Dependencies: 186
-- Data for Name: adm_function; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.adm_function VALUES (3, 'menu1-2', 'menu1-2', true, NULL, NULL, NULL, 1, 'menu-1-2', NULL);
INSERT INTO public.adm_function VALUES (4, 'menu1-3', 'menu1-3', true, NULL, NULL, NULL, 1, 'menu-1-3', NULL);
INSERT INTO public.adm_function VALUES (5, 'menu1-4', 'menu1-4', true, NULL, NULL, NULL, 1, 'menu-1-4', NULL);
INSERT INTO public.adm_function VALUES (6, 'menu1-2-1', 'menu1-2-1', true, NULL, NULL, NULL, 2, 'menu1-2-1', NULL);
INSERT INTO public.adm_function VALUES (7, 'menu1-2-2', 'menu1-2-2', true, NULL, NULL, NULL, 2, 'menu1-2-2', NULL);
INSERT INTO public.adm_function VALUES (8, 'menu1-2-3', 'menu1-2-3', true, NULL, NULL, NULL, 2, 'menu1-2-3', NULL);
INSERT INTO public.adm_function VALUES (1, 'menu1', 'menu1', true, NULL, NULL, NULL, NULL, '', NULL);
INSERT INTO public.adm_function VALUES (2, 'menu1-1', 'menu1-1', true, NULL, NULL, NULL, 1, '', NULL);
INSERT INTO public.adm_function VALUES (9, 'menu1-2-4', 'menu1-2-4', true, NULL, NULL, NULL, 2, '', NULL);
INSERT INTO public.adm_function VALUES (11, 'menu1-2-4-1-1', 'menu1-2-4-1-1', true, NULL, NULL, '', 10, 'menu1-2-4-1-1', NULL);
INSERT INTO public.adm_function VALUES (10, 'menu1-2-4-1', 'menu1-2-4-1', true, NULL, NULL, NULL, 9, '', NULL);
INSERT INTO public.adm_function VALUES (12, 'menu12', 'menu12', true, NULL, NULL, NULL, NULL, '', NULL);
INSERT INTO public.adm_function VALUES (13, 'menu13', 'menu13', true, NULL, NULL, NULL, 12, '', NULL);
INSERT INTO public.adm_function VALUES (15, 'menu15', 'menu15', true, NULL, NULL, NULL, 14, '', NULL);
INSERT INTO public.adm_function VALUES (14, 'menu14', 'menu14', true, NULL, NULL, NULL, 13, '', NULL);
INSERT INTO public.adm_function VALUES (16, 'menu16', 'menu16', true, NULL, NULL, NULL, 15, '', NULL);
INSERT INTO public.adm_function VALUES (17, 'menu17', 'menu17', true, NULL, NULL, NULL, 16, '', NULL);
INSERT INTO public.adm_function VALUES (18, 'menu18', 'menu18', true, NULL, NULL, NULL, 17, '', NULL);
INSERT INTO public.adm_function VALUES (19, 'menu19', 'menu19', true, NULL, NULL, NULL, 18, '', NULL);
INSERT INTO public.adm_function VALUES (20, 'menu20', 'menu20', true, NULL, NULL, NULL, 19, 'menu20', NULL);
INSERT INTO public.adm_function VALUES (21, 'menu16-1', 'menu16-1', true, NULL, NULL, NULL, 16, 'menu16-1', NULL);


--
-- TOC entry 2221 (class 0 OID 108009)
-- Dependencies: 187
-- Data for Name: adm_profile; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.adm_profile VALUES ('6fc2432c-b355-41c1-9fdf-5ca69cbfe5fc', 'ADMIN', 'ADMIN', 'Administrateur', '2020-09-15 09:35:36.171', NULL);
INSERT INTO public.adm_profile VALUES ('a47123c0-57b9-4920-9881-0cb1cde827eb', 'USER', 'USER', 'User', '2020-09-15 09:42:38.146', NULL);


--
-- TOC entry 2223 (class 0 OID 108026)
-- Dependencies: 189
-- Data for Name: adm_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.adm_user VALUES ('76e23153-5e21-43cd-921e-2364ebad8694', 'admin', '$2a$10$jPCihk.CtsI9Y66ZzijuIOGe3dr/7Ky1rc0Jv1Jf2EAozPz68S7Mi', '2020-09-17 14:08:28.08', NULL, 1, '6fc2432c-b355-41c1-9fdf-5ca69cbfe5fc', '51ab35f9-4d73-4308-a1f0-df4be7561c42', '2022-09-17 23:59:59', NULL, false);
INSERT INTO public.adm_user VALUES ('cd6da1ee-0035-436b-bd19-f43ec3645c2b', 'yassine', '$2a$10$XfSpQTTApODAKtjDCflpHOfpoySFQupIQINJYKsNUx0d02si6nP76', '2020-09-23 10:05:26.971', '2020-09-23 10:15:53.987', 1, 'a47123c0-57b9-4920-9881-0cb1cde827eb', '8f2937ca-7f60-428e-8a33-ef609445017f', '2022-09-23 10:04:30.082', '192.168.1.152', false);
INSERT INTO public.adm_user VALUES ('cdc1db73-d9a1-4f67-95eb-95457efbf512', 'user', '$2a$10$00Ax0E3gPn8sSzXeXXPKIeA9cfgwveP.DE0VTNVXo735nvxdjz.Eu', '2020-09-18 10:50:02.387', NULL, 1, 'a47123c0-57b9-4920-9881-0cb1cde827eb', '9e1a8a78-6f03-4210-b29b-259a86004802', '2022-09-17 23:59:59', NULL, false);


--
-- TOC entry 2231 (class 0 OID 116076)
-- Dependencies: 200
-- Data for Name: adm_user_historique; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2224 (class 0 OID 108048)
-- Dependencies: 190
-- Data for Name: function_profile; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.function_profile VALUES (1, 'a47123c0-57b9-4920-9881-0cb1cde827eb', 1);
INSERT INTO public.function_profile VALUES (2, 'a47123c0-57b9-4920-9881-0cb1cde827eb', 3);
INSERT INTO public.function_profile VALUES (3, 'a47123c0-57b9-4920-9881-0cb1cde827eb', 4);


--
-- TOC entry 2225 (class 0 OID 108063)
-- Dependencies: 191
-- Data for Name: log_access; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.log_access VALUES (1, '461', '2020-09-20 12:50:01.494', 'yassine', NULL, NULL);
INSERT INTO public.log_access VALUES (2, '200', '2020-09-20 12:54:19.492', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', NULL);
INSERT INTO public.log_access VALUES (3, '202', '2020-09-20 13:45:30.893', 'ahmed', 'cdc1db73-d9a1-4f67-95eb-95457efbf512', NULL);
INSERT INTO public.log_access VALUES (4, '202', '2020-09-20 13:48:22.136', 'ahmed', 'cdc1db73-d9a1-4f67-95eb-95457efbf512', NULL);
INSERT INTO public.log_access VALUES (5, '202', '2020-09-20 13:48:47.613', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', NULL);
INSERT INTO public.log_access VALUES (6, '200', '2020-09-20 13:49:07.153', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', NULL);
INSERT INTO public.log_access VALUES (7, '200', '2020-09-20 13:49:12.706', 'ahmed', 'cdc1db73-d9a1-4f67-95eb-95457efbf512', NULL);
INSERT INTO public.log_access VALUES (8, '200', '2020-09-20 13:49:28.103', 'ahmed', 'cdc1db73-d9a1-4f67-95eb-95457efbf512', NULL);
INSERT INTO public.log_access VALUES (9, '200', '2020-09-20 13:49:29.941', 'ahmed', 'cdc1db73-d9a1-4f67-95eb-95457efbf512', NULL);
INSERT INTO public.log_access VALUES (10, '200', '2020-09-20 13:49:57.757', 'ahmed', 'cdc1db73-d9a1-4f67-95eb-95457efbf512', NULL);
INSERT INTO public.log_access VALUES (11, '200', '2020-09-20 13:51:26.35', 'ahmed', 'cdc1db73-d9a1-4f67-95eb-95457efbf512', NULL);
INSERT INTO public.log_access VALUES (12, '200', '2020-09-20 13:51:32.087', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', NULL);
INSERT INTO public.log_access VALUES (13, '200', '2020-09-20 13:51:49.945', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', NULL);
INSERT INTO public.log_access VALUES (14, '202', '2020-09-20 13:51:54.172', 'ahmed', 'cdc1db73-d9a1-4f67-95eb-95457efbf512', NULL);
INSERT INTO public.log_access VALUES (15, '202', '2020-09-20 13:52:03.193', 'ahmed', 'cdc1db73-d9a1-4f67-95eb-95457efbf512', NULL);
INSERT INTO public.log_access VALUES (16, '202', '2020-09-20 13:52:23.897', 'ahmed', 'cdc1db73-d9a1-4f67-95eb-95457efbf512', NULL);
INSERT INTO public.log_access VALUES (17, '200', '2020-09-20 13:53:38.029', 'ahmed', 'cdc1db73-d9a1-4f67-95eb-95457efbf512', NULL);
INSERT INTO public.log_access VALUES (18, '200', '2020-09-20 13:53:49.783', 'ahmed', 'cdc1db73-d9a1-4f67-95eb-95457efbf512', NULL);
INSERT INTO public.log_access VALUES (19, '202', '2020-09-20 13:54:14.737', 'ahmed', 'cdc1db73-d9a1-4f67-95eb-95457efbf512', NULL);
INSERT INTO public.log_access VALUES (20, '200', '2020-09-20 13:54:31.682', 'ahmed', 'cdc1db73-d9a1-4f67-95eb-95457efbf512', NULL);
INSERT INTO public.log_access VALUES (21, '202', '2020-09-20 13:54:38.846', 'ahmed', 'cdc1db73-d9a1-4f67-95eb-95457efbf512', NULL);
INSERT INTO public.log_access VALUES (22, '200', '2020-09-20 13:55:07.229', 'ahmed', 'cdc1db73-d9a1-4f67-95eb-95457efbf512', NULL);
INSERT INTO public.log_access VALUES (23, '202', '2020-09-20 13:55:16', 'ahmed', 'cdc1db73-d9a1-4f67-95eb-95457efbf512', NULL);
INSERT INTO public.log_access VALUES (24, '201', '2020-09-20 13:56:53.643', 'ahmed', 'cdc1db73-d9a1-4f67-95eb-95457efbf512', NULL);
INSERT INTO public.log_access VALUES (25, '461', '2020-09-20 13:57:28.878', 'ahmed7', NULL, NULL);
INSERT INTO public.log_access VALUES (26, '200', '2020-09-21 10:12:35.212', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', NULL);
INSERT INTO public.log_access VALUES (27, '200', '2020-09-21 10:15:13.539', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', NULL);
INSERT INTO public.log_access VALUES (28, '200', '2020-09-21 10:15:23.627', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', NULL);
INSERT INTO public.log_access VALUES (29, '200', '2020-09-21 10:38:11.416', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', NULL);
INSERT INTO public.log_access VALUES (30, '200', '2020-09-21 10:38:59.229', 'ahmed', 'cdc1db73-d9a1-4f67-95eb-95457efbf512', NULL);
INSERT INTO public.log_access VALUES (31, '200', '2020-09-21 10:38:59.982', 'ahmed', 'cdc1db73-d9a1-4f67-95eb-95457efbf512', NULL);
INSERT INTO public.log_access VALUES (32, '200', '2020-09-21 10:41:45.25', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', NULL);
INSERT INTO public.log_access VALUES (33, '200', '2020-09-21 10:41:45.672', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', NULL);
INSERT INTO public.log_access VALUES (34, '200', '2020-09-21 17:12:54.287', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', NULL);
INSERT INTO public.log_access VALUES (35, '200', '2020-09-22 08:39:18.396', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', NULL);
INSERT INTO public.log_access VALUES (36, '200', '2020-09-22 08:40:21.946', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', NULL);
INSERT INTO public.log_access VALUES (37, '200', '2020-09-22 09:08:02.529', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.1.152');
INSERT INTO public.log_access VALUES (38, '200', '2020-09-22 09:11:35.862', 'ahmed', 'cdc1db73-d9a1-4f67-95eb-95457efbf512', '192.168.1.152');
INSERT INTO public.log_access VALUES (39, '461', '2020-09-22 09:11:38.215', 'ahmed', NULL, '192.168.1.152');
INSERT INTO public.log_access VALUES (40, '200', '2020-09-22 09:13:11.991', 'ahmed', 'cdc1db73-d9a1-4f67-95eb-95457efbf512', '192.168.1.110');
INSERT INTO public.log_access VALUES (41, '200', '2020-09-22 09:26:29.004', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.1.152');
INSERT INTO public.log_access VALUES (42, '200', '2020-09-23 10:06:11.481', 'omar', 'cd6da1ee-0035-436b-bd19-f43ec3645c2b', '192.168.1.152');
INSERT INTO public.log_access VALUES (43, '200', '2020-09-23 10:07:00.321', 'omar', 'cd6da1ee-0035-436b-bd19-f43ec3645c2b', '192.168.1.152');
INSERT INTO public.log_access VALUES (44, '200', '2020-09-23 10:15:03.032', 'omar', 'cd6da1ee-0035-436b-bd19-f43ec3645c2b', '192.168.1.152');
INSERT INTO public.log_access VALUES (45, '461', '2020-09-23 10:15:16.802', 'omar', NULL, '192.168.1.152');
INSERT INTO public.log_access VALUES (46, '200', '2020-09-23 10:15:20.453', 'omar', 'cd6da1ee-0035-436b-bd19-f43ec3645c2b', '192.168.1.152');
INSERT INTO public.log_access VALUES (47, '200', '2020-09-23 11:02:39.614', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.1.152');
INSERT INTO public.log_access VALUES (48, '200', '2020-09-23 11:03:39.525', 'omar', 'cd6da1ee-0035-436b-bd19-f43ec3645c2b', '192.168.1.152');
INSERT INTO public.log_access VALUES (49, '200', '2020-09-23 15:02:06.083', 'ahmed', 'cdc1db73-d9a1-4f67-95eb-95457efbf512', '192.168.1.152');
INSERT INTO public.log_access VALUES (50, '200', '2020-09-23 17:00:43.793', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.1.152');
INSERT INTO public.log_access VALUES (51, '200', '2020-09-24 08:39:52.057', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.1.152');
INSERT INTO public.log_access VALUES (52, '200', '2020-09-24 08:55:27.63', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.1.152');
INSERT INTO public.log_access VALUES (53, '200', '2020-09-24 09:42:37.44', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.1.152');
INSERT INTO public.log_access VALUES (54, '200', '2020-09-25 08:32:41.591', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.1.152');
INSERT INTO public.log_access VALUES (55, '200', '2020-09-25 09:22:38.309', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.1.152');
INSERT INTO public.log_access VALUES (56, '200', '2020-09-25 18:03:39.039', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.137.116');
INSERT INTO public.log_access VALUES (57, '200', '2020-09-26 17:08:29.771', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.137.1');
INSERT INTO public.log_access VALUES (58, '200', '2020-09-26 17:10:35.607', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.137.1');
INSERT INTO public.log_access VALUES (59, '200', '2020-09-26 17:15:18.103', 'ahmed', 'cdc1db73-d9a1-4f67-95eb-95457efbf512', '192.168.137.1');
INSERT INTO public.log_access VALUES (60, '200', '2020-09-26 17:36:36.578', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.137.1');
INSERT INTO public.log_access VALUES (61, '200', '2020-09-27 10:24:12.001', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.137.1');
INSERT INTO public.log_access VALUES (62, '200', '2020-09-27 12:10:23.62', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.137.1');
INSERT INTO public.log_access VALUES (63, '200', '2020-09-29 08:57:11.732', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.1.152');
INSERT INTO public.log_access VALUES (64, '200', '2020-09-29 18:40:09.372', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.1.152');
INSERT INTO public.log_access VALUES (65, '200', '2020-09-30 09:46:19.949', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.1.152');
INSERT INTO public.log_access VALUES (66, '200', '2020-10-01 10:16:17.218', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.1.152');
INSERT INTO public.log_access VALUES (67, '200', '2020-10-02 11:16:02.423', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.1.152');
INSERT INTO public.log_access VALUES (68, '200', '2020-10-02 11:36:49.815', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.1.152');
INSERT INTO public.log_access VALUES (69, '461', '2020-10-02 12:01:33.659', 'yassine', NULL, '192.168.1.152');
INSERT INTO public.log_access VALUES (70, '461', '2020-10-02 12:01:43.066', 'yassine', NULL, '192.168.1.152');
INSERT INTO public.log_access VALUES (71, '461', '2020-10-02 12:02:08.29', 'yassine', NULL, '192.168.1.152');
INSERT INTO public.log_access VALUES (72, '200', '2020-10-02 12:02:27.571', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.1.152');
INSERT INTO public.log_access VALUES (73, '200', '2020-10-04 11:50:02.266', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.137.1');
INSERT INTO public.log_access VALUES (74, '200', '2020-10-04 12:10:56.981', 'ahmed', 'cdc1db73-d9a1-4f67-95eb-95457efbf512', '192.168.137.1');
INSERT INTO public.log_access VALUES (75, '200', '2020-10-04 12:11:28.507', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.137.1');
INSERT INTO public.log_access VALUES (76, '200', '2020-10-04 12:16:35.868', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.137.1');
INSERT INTO public.log_access VALUES (77, '200', '2020-10-04 12:23:27.246', 'ahmed', 'cdc1db73-d9a1-4f67-95eb-95457efbf512', '192.168.137.1');
INSERT INTO public.log_access VALUES (78, '200', '2020-10-05 15:29:31.498', 'ahmed', 'cdc1db73-d9a1-4f67-95eb-95457efbf512', '192.168.1.152');
INSERT INTO public.log_access VALUES (79, '200', '2020-10-08 11:44:41.101', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.1.152');
INSERT INTO public.log_access VALUES (80, '200', '2020-10-13 19:39:27.394', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.1.58');
INSERT INTO public.log_access VALUES (81, '461', '2020-10-13 19:41:11.21', 'aa', NULL, '192.168.1.58');
INSERT INTO public.log_access VALUES (82, '200', '2020-10-13 19:46:31.424', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.1.58');
INSERT INTO public.log_access VALUES (83, '200', '2020-10-13 19:56:39.484', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.1.58');
INSERT INTO public.log_access VALUES (84, '200', '2020-10-13 20:03:40.188', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.1.58');
INSERT INTO public.log_access VALUES (85, '200', '2020-10-13 20:10:41.707', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.1.58');
INSERT INTO public.log_access VALUES (86, '200', '2020-10-13 20:32:29.359', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.1.58');
INSERT INTO public.log_access VALUES (87, '200', '2020-10-14 15:45:58.526', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.1.152');
INSERT INTO public.log_access VALUES (88, '200', '2020-10-16 16:16:58.386', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.1.152');
INSERT INTO public.log_access VALUES (89, '200', '2020-10-18 18:57:57.553', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.137.1');
INSERT INTO public.log_access VALUES (90, '200', '2020-10-18 18:57:58.643', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.137.1');
INSERT INTO public.log_access VALUES (91, '200', '2020-10-19 10:45:06.531', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.137.1');
INSERT INTO public.log_access VALUES (92, '200', '2020-10-19 10:54:06.577', 'yassine', '76e23153-5e21-43cd-921e-2364ebad8694', '192.168.137.1');


--
-- TOC entry 2226 (class 0 OID 108068)
-- Dependencies: 192
-- Data for Name: log_data; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.log_data VALUES (2, '2020-09-20 10:45:14.544', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/administration/user', '200');
INSERT INTO public.log_data VALUES (3, '2020-09-20 10:52:27.743', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/administration/user', '200');
INSERT INTO public.log_data VALUES (4, '2020-09-20 10:53:21.271', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/administration/user', '200');
INSERT INTO public.log_data VALUES (7, '2020-09-20 11:47:08.395', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/nomenclature/adm/profile', '200');
INSERT INTO public.log_data VALUES (8, '2020-09-20 11:47:23.355', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/nomenclature/adm/profile/code', '200');
INSERT INTO public.log_data VALUES (9, '2020-09-20 11:47:26.739', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/nomenclature/adm/profile/code', '200');
INSERT INTO public.log_data VALUES (10, '2020-09-20 11:47:56.55', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/nomenclature/adm/profile', '425');
INSERT INTO public.log_data VALUES (11, '2020-09-21 10:15:44.738', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/administration/user', '420');
INSERT INTO public.log_data VALUES (12, '2020-09-21 10:17:39.031', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/administration/user', '420');
INSERT INTO public.log_data VALUES (13, '2020-09-21 10:24:02.281', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/administration/user', '420');
INSERT INTO public.log_data VALUES (14, '2020-09-21 10:25:52.809', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/administration/user', '421');
INSERT INTO public.log_data VALUES (15, '2020-09-21 10:26:48.712', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/administration/user', '421');
INSERT INTO public.log_data VALUES (16, '2020-09-21 10:27:47.396', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/administration/user', '421');
INSERT INTO public.log_data VALUES (17, '2020-09-21 10:36:14.193', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/administration/user', '420');
INSERT INTO public.log_data VALUES (18, '2020-09-21 10:37:09.271', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/administration/user', '420');
INSERT INTO public.log_data VALUES (19, '2020-09-21 10:37:15.502', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/administration/user', '420');
INSERT INTO public.log_data VALUES (20, '2020-09-21 10:38:50.199', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/nomenclature/adm/profile', '200');
INSERT INTO public.log_data VALUES (21, '2020-09-21 10:42:25.364', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/administration/user', '420');
INSERT INTO public.log_data VALUES (22, '2020-09-21 10:45:26.429', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/administration/user', '200');
INSERT INTO public.log_data VALUES (23, '2020-09-21 10:45:32.39', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/administration/user', '200');
INSERT INTO public.log_data VALUES (24, '2020-09-21 10:48:11.077', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/administration/user', '200');
INSERT INTO public.log_data VALUES (25, '2020-09-21 17:14:22.149', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '420');
INSERT INTO public.log_data VALUES (26, '2020-09-21 17:15:45.253', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '200');
INSERT INTO public.log_data VALUES (27, '2020-09-21 17:15:58.14', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '200');
INSERT INTO public.log_data VALUES (28, '2020-09-21 17:16:03.523', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '200');
INSERT INTO public.log_data VALUES (29, '2020-09-21 17:16:08.531', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '200');
INSERT INTO public.log_data VALUES (30, '2020-09-22 09:26:42.602', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/whoiam', '421');
INSERT INTO public.log_data VALUES (31, '2020-09-22 09:27:59.54', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/whoiam', '421');
INSERT INTO public.log_data VALUES (32, '2020-09-22 09:28:08.117', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/whoiam', '200');
INSERT INTO public.log_data VALUES (33, '2020-09-22 09:31:44.525', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/whoiam', '200');
INSERT INTO public.log_data VALUES (34, '2020-09-22 09:32:07.857', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '200');
INSERT INTO public.log_data VALUES (35, '2020-09-22 09:32:43.484', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (36, '2020-09-22 09:32:52.13', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/whoiam', '200');
INSERT INTO public.log_data VALUES (37, '2020-09-23 10:07:33.661', '192.168.1.152', 'cd6da1ee-0035-436b-bd19-f43ec3645c2b', 'PUT', '/reset/password', '422');
INSERT INTO public.log_data VALUES (38, '2020-09-23 10:09:00.032', '192.168.1.152', 'cd6da1ee-0035-436b-bd19-f43ec3645c2b', 'PUT', '/reset/password', '422');
INSERT INTO public.log_data VALUES (39, '2020-09-23 10:09:24.07', '192.168.1.152', 'cd6da1ee-0035-436b-bd19-f43ec3645c2b', 'PUT', '/reset/password', '422');
INSERT INTO public.log_data VALUES (40, '2020-09-23 10:12:05.254', '192.168.1.152', 'cd6da1ee-0035-436b-bd19-f43ec3645c2b', 'PUT', '/reset/password', '422');
INSERT INTO public.log_data VALUES (41, '2020-09-23 10:12:55.991', '192.168.1.152', 'cd6da1ee-0035-436b-bd19-f43ec3645c2b', 'PUT', '/reset/password', '462');
INSERT INTO public.log_data VALUES (42, '2020-09-23 10:13:50.469', '192.168.1.152', 'cd6da1ee-0035-436b-bd19-f43ec3645c2b', 'PUT', '/reset/password', '200');
INSERT INTO public.log_data VALUES (43, '2020-09-23 10:14:22.624', '192.168.1.152', 'cd6da1ee-0035-436b-bd19-f43ec3645c2b', 'PUT', '/reset/password', '461');
INSERT INTO public.log_data VALUES (44, '2020-09-23 10:14:51.406', '192.168.1.152', 'cd6da1ee-0035-436b-bd19-f43ec3645c2b', 'PUT', '/reset/password', '200');
INSERT INTO public.log_data VALUES (45, '2020-09-23 10:15:11.2', '192.168.1.152', 'cd6da1ee-0035-436b-bd19-f43ec3645c2b', 'PUT', '/reset/password', '200');
INSERT INTO public.log_data VALUES (46, '2020-09-23 10:15:34.169', '192.168.1.152', 'cd6da1ee-0035-436b-bd19-f43ec3645c2b', 'PUT', '/reset/password', '462');
INSERT INTO public.log_data VALUES (47, '2020-09-23 10:15:42.411', '192.168.1.152', 'cd6da1ee-0035-436b-bd19-f43ec3645c2b', 'PUT', '/reset/password', '461');
INSERT INTO public.log_data VALUES (48, '2020-09-23 10:15:53.991', '192.168.1.152', 'cd6da1ee-0035-436b-bd19-f43ec3645c2b', 'PUT', '/reset/password', '200');
INSERT INTO public.log_data VALUES (49, '2020-09-23 11:02:55.228', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/profile', '200');
INSERT INTO public.log_data VALUES (50, '2020-09-23 11:03:29.93', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/profile', '200');
INSERT INTO public.log_data VALUES (51, '2020-09-23 15:02:17.214', '192.168.1.152', 'cdc1db73-d9a1-4f67-95eb-95457efbf512', 'GET', '/profile', '200');
INSERT INTO public.log_data VALUES (52, '2020-09-23 17:01:14.755', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '420');
INSERT INTO public.log_data VALUES (53, '2020-09-23 17:01:41.562', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '200');
INSERT INTO public.log_data VALUES (54, '2020-09-24 08:40:06.6', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '420');
INSERT INTO public.log_data VALUES (55, '2020-09-24 08:40:12.705', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '420');
INSERT INTO public.log_data VALUES (56, '2020-09-24 08:41:20.889', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '420');
INSERT INTO public.log_data VALUES (57, '2020-09-24 08:45:51.849', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '420');
INSERT INTO public.log_data VALUES (58, '2020-09-24 08:46:12.181', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '200');
INSERT INTO public.log_data VALUES (59, '2020-09-24 08:49:45.752', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '200');
INSERT INTO public.log_data VALUES (60, '2020-09-24 08:50:04.753', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (61, '2020-09-24 08:50:14.335', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '420');
INSERT INTO public.log_data VALUES (62, '2020-09-24 08:54:46.971', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '200');
INSERT INTO public.log_data VALUES (63, '2020-09-24 08:54:57.021', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '200');
INSERT INTO public.log_data VALUES (64, '2020-09-24 08:55:39.991', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (65, '2020-09-24 08:55:42.266', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (66, '2020-09-24 08:56:10.308', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (67, '2020-09-24 08:56:22.15', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (68, '2020-09-24 08:56:49.815', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (69, '2020-09-24 08:57:21.234', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (70, '2020-09-24 08:58:55.535', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (71, '2020-09-24 08:59:08.469', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (72, '2020-09-24 09:04:34.077', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (73, '2020-09-24 09:04:39.114', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (74, '2020-09-24 09:19:50.588', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (75, '2020-09-24 09:20:00.178', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (76, '2020-09-24 09:20:05.409', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (77, '2020-09-24 09:20:12.01', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (78, '2020-09-24 09:20:57.941', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '200');
INSERT INTO public.log_data VALUES (79, '2020-09-24 09:21:04.31', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '200');
INSERT INTO public.log_data VALUES (80, '2020-09-24 09:21:11.489', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '200');
INSERT INTO public.log_data VALUES (81, '2020-09-24 09:21:19.4', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '200');
INSERT INTO public.log_data VALUES (82, '2020-09-24 09:21:37.378', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '200');
INSERT INTO public.log_data VALUES (83, '2020-09-24 09:21:47.593', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '200');
INSERT INTO public.log_data VALUES (84, '2020-09-24 09:21:59.179', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '200');
INSERT INTO public.log_data VALUES (85, '2020-09-24 09:22:01.724', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '200');
INSERT INTO public.log_data VALUES (86, '2020-09-24 09:42:55.534', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/whoiam', '200');
INSERT INTO public.log_data VALUES (87, '2020-09-24 09:43:40.922', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/whoiam', '200');
INSERT INTO public.log_data VALUES (88, '2020-09-24 09:44:06.691', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (89, '2020-09-24 09:44:13.818', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (90, '2020-09-24 09:44:46.582', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/profile/account/desactive/{id}', '200');
INSERT INTO public.log_data VALUES (91, '2020-09-24 09:45:18.922', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/profile/account/reactive/{id}', '200');
INSERT INTO public.log_data VALUES (92, '2020-09-24 09:52:19.5', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/profile/account/reactive/{id}', '200');
INSERT INTO public.log_data VALUES (93, '2020-09-25 08:33:12.57', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (94, '2020-09-25 08:33:55.975', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/profile/account/desactive/{id}', '200');
INSERT INTO public.log_data VALUES (95, '2020-09-25 08:34:09.117', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (96, '2020-09-25 08:36:17.8', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/profile/account/reactive/{id}', '200');
INSERT INTO public.log_data VALUES (97, '2020-09-25 08:36:36.751', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'DELETE', '/profile/account/{id}', '427');
INSERT INTO public.log_data VALUES (98, '2020-09-25 18:04:37.428', '192.168.137.116', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/whoiam', '200');
INSERT INTO public.log_data VALUES (99, '2020-09-25 18:05:05.969', '192.168.137.116', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '200');
INSERT INTO public.log_data VALUES (100, '2020-09-26 17:09:21.217', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (101, '2020-09-26 17:36:50.102', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (102, '2020-09-26 18:49:35.686', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '200');
INSERT INTO public.log_data VALUES (103, '2020-09-26 18:50:19.016', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '200');
INSERT INTO public.log_data VALUES (104, '2020-09-26 18:50:49.66', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '200');
INSERT INTO public.log_data VALUES (105, '2020-09-26 18:51:02.766', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '200');
INSERT INTO public.log_data VALUES (106, '2020-09-27 10:26:02.492', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (107, '2020-09-27 10:26:35.143', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (108, '2020-09-27 10:30:18.281', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (109, '2020-09-27 10:31:12.451', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (110, '2020-09-27 10:31:29.058', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (111, '2020-09-27 10:33:28.791', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (112, '2020-09-27 10:33:46.979', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (113, '2020-09-27 10:34:00.993', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (114, '2020-09-27 10:35:01.851', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (115, '2020-09-27 10:35:16.687', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (116, '2020-09-27 10:35:42.108', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (117, '2020-09-27 10:36:52.629', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (118, '2020-09-27 10:37:01.589', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (119, '2020-09-27 10:39:11.008', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (120, '2020-09-27 10:51:59.46', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (121, '2020-09-27 11:00:44.311', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (122, '2020-09-27 11:00:44.945', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (123, '2020-09-27 11:01:22.851', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (124, '2020-09-27 11:01:39.901', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (125, '2020-09-27 11:02:30.973', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (126, '2020-09-27 11:04:13.239', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (127, '2020-09-27 11:05:01.686', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (128, '2020-09-27 11:05:36.96', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (129, '2020-09-27 11:05:56.576', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (130, '2020-09-27 11:06:10.053', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (131, '2020-09-27 11:06:35.171', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (132, '2020-09-27 11:06:51.99', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (133, '2020-09-27 11:07:37.758', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (134, '2020-09-27 11:08:07.691', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (135, '2020-09-27 11:12:27.001', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (136, '2020-09-27 11:17:21.978', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (137, '2020-09-27 12:10:57.323', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access/v2', '200');
INSERT INTO public.log_data VALUES (138, '2020-09-27 12:13:48.181', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access/v2', '200');
INSERT INTO public.log_data VALUES (139, '2020-09-27 12:14:31.24', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access/v2', '200');
INSERT INTO public.log_data VALUES (140, '2020-09-27 12:15:19.409', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access/v2', '200');
INSERT INTO public.log_data VALUES (141, '2020-09-27 12:15:39.586', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access/v2', '200');
INSERT INTO public.log_data VALUES (142, '2020-09-27 12:16:03.759', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access/v2', '200');
INSERT INTO public.log_data VALUES (143, '2020-09-27 12:16:09.966', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access/v2', '200');
INSERT INTO public.log_data VALUES (144, '2020-09-27 12:16:50.912', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access/v2', '200');
INSERT INTO public.log_data VALUES (145, '2020-09-27 12:17:53.749', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access/v2', '200');
INSERT INTO public.log_data VALUES (146, '2020-09-27 12:18:08.222', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access/v2', '200');
INSERT INTO public.log_data VALUES (147, '2020-09-27 12:18:49.406', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access/v2', '200');
INSERT INTO public.log_data VALUES (148, '2020-09-27 12:19:07.566', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access/v2', '200');
INSERT INTO public.log_data VALUES (149, '2020-09-27 12:19:28.386', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access/v2', '200');
INSERT INTO public.log_data VALUES (150, '2020-09-27 12:20:06.861', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access/v2', '200');
INSERT INTO public.log_data VALUES (151, '2020-09-27 12:20:48.726', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access/v2', '200');
INSERT INTO public.log_data VALUES (152, '2020-09-27 12:21:14.184', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access/v2', '200');
INSERT INTO public.log_data VALUES (153, '2020-09-27 12:26:03.057', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (154, '2020-09-27 18:56:42.088', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (155, '2020-09-27 18:58:11.824', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (156, '2020-09-27 19:05:17.307', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (157, '2020-09-27 19:05:30.024', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (158, '2020-09-27 19:05:46.755', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (159, '2020-09-27 19:07:53.266', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (160, '2020-09-27 19:07:57.895', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (161, '2020-09-27 19:08:01.744', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (162, '2020-09-27 19:08:56.892', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (163, '2020-09-27 19:09:05.761', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (164, '2020-09-29 08:57:56.47', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/personnel/page', '200');
INSERT INTO public.log_data VALUES (165, '2020-09-29 08:58:05.169', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/personnel/page', '200');
INSERT INTO public.log_data VALUES (166, '2020-09-29 08:58:08.98', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/personnel/page', '200');
INSERT INTO public.log_data VALUES (167, '2020-09-29 08:58:13.436', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/personnel/page', '200');
INSERT INTO public.log_data VALUES (168, '2020-09-29 09:11:59.746', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/personnel/page', '200');
INSERT INTO public.log_data VALUES (169, '2020-09-29 09:12:03.9', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/personnel/page', '200');
INSERT INTO public.log_data VALUES (170, '2020-09-29 09:12:32.91', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/personnel', '200');
INSERT INTO public.log_data VALUES (171, '2020-09-29 09:13:04.552', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'PUT', '/administration/personnel', '200');
INSERT INTO public.log_data VALUES (172, '2020-09-29 09:13:28.447', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/administration/personnel', '200');
INSERT INTO public.log_data VALUES (173, '2020-09-29 09:13:54.784', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/administration/personnel/{id}', '422');
INSERT INTO public.log_data VALUES (174, '2020-09-29 09:15:03.26', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/administration/personnel/{id}', '422');
INSERT INTO public.log_data VALUES (175, '2020-09-29 09:15:11.378', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'DELETE', '/administration/personnel/{id}', '200');
INSERT INTO public.log_data VALUES (176, '2020-09-29 09:15:13.931', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'DELETE', '/administration/personnel/{id}', '200');
INSERT INTO public.log_data VALUES (177, '2020-09-29 09:15:14.709', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'DELETE', '/administration/personnel/{id}', '200');
INSERT INTO public.log_data VALUES (178, '2020-09-29 09:15:15.396', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'DELETE', '/administration/personnel/{id}', '200');
INSERT INTO public.log_data VALUES (179, '2020-09-29 09:15:35.303', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/administration/personnel/{id}', '422');
INSERT INTO public.log_data VALUES (180, '2020-09-29 18:40:45.232', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/whoiam', '200');
INSERT INTO public.log_data VALUES (181, '2020-09-29 18:41:20.56', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (182, '2020-09-29 18:41:38.352', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (183, '2020-09-29 18:41:52.111', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (184, '2020-09-29 18:42:13.997', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (185, '2020-09-29 18:42:32.713', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '200');
INSERT INTO public.log_data VALUES (186, '2020-09-29 18:42:50.197', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '200');
INSERT INTO public.log_data VALUES (187, '2020-09-29 18:45:24.289', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (188, '2020-09-30 09:47:04.713', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (189, '2020-09-30 09:47:24.77', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (190, '2020-09-30 09:49:41.202', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (191, '2020-10-01 10:16:43.962', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (192, '2020-10-01 10:16:53.411', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (193, '2020-10-01 10:16:58.706', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (194, '2020-10-01 10:17:32.948', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (195, '2020-10-01 10:17:39.336', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (196, '2020-10-01 10:17:49.125', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (197, '2020-10-01 10:17:54.985', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (198, '2020-10-01 10:18:45.203', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '200');
INSERT INTO public.log_data VALUES (199, '2020-10-02 11:16:24.52', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (200, '2020-10-02 11:17:46.039', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (201, '2020-10-02 11:21:24.692', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (202, '2020-10-02 11:21:43.475', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (203, '2020-10-02 11:22:15.073', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '200');
INSERT INTO public.log_data VALUES (204, '2020-10-02 11:23:33.443', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (205, '2020-10-02 11:25:11.884', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (206, '2020-10-02 11:25:56.228', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (207, '2020-10-02 11:26:55.378', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (208, '2020-10-02 11:28:35.912', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (209, '2020-10-02 11:28:57.284', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (210, '2020-10-02 11:29:12.1', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (211, '2020-10-02 11:29:42.801', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (212, '2020-10-02 11:31:10.024', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (213, '2020-10-02 11:32:02.387', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (214, '2020-10-02 11:33:40.665', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (215, '2020-10-02 11:37:30.355', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (216, '2020-10-02 11:39:43.524', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (217, '2020-10-02 11:42:29.269', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (218, '2020-10-02 11:45:06.524', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (219, '2020-10-02 11:47:32.221', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (220, '2020-10-02 11:49:52.3', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (221, '2020-10-02 11:55:14.42', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (222, '2020-10-02 11:56:16.38', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (223, '2020-10-02 12:02:39.484', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (224, '2020-10-02 12:06:27.2', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (225, '2020-10-02 12:07:44.547', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (226, '2020-10-02 12:17:47.034', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (227, '2020-10-02 12:20:20.54', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (228, '2020-10-02 12:21:24.192', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (229, '2020-10-02 12:22:48.444', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (230, '2020-10-02 12:23:12.586', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (231, '2020-10-02 12:23:33.161', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (232, '2020-10-02 12:23:53.124', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (233, '2020-10-02 12:24:23.397', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (234, '2020-10-02 12:25:49.103', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (235, '2020-10-02 12:27:01.474', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (236, '2020-10-02 12:27:18.25', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (237, '2020-10-02 12:27:36.903', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '200');
INSERT INTO public.log_data VALUES (238, '2020-10-02 12:33:18.792', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (239, '2020-10-02 12:33:50.76', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (240, '2020-10-02 12:34:12.023', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (241, '2020-10-02 12:36:05.759', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (242, '2020-10-02 12:37:54.259', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (243, '2020-10-02 12:42:01.85', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (244, '2020-10-02 12:46:36.936', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (245, '2020-10-02 14:16:00.261', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (246, '2020-10-02 14:42:21.855', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (247, '2020-10-02 14:43:27.359', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (248, '2020-10-02 14:44:26.156', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (249, '2020-10-02 14:48:42.107', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (250, '2020-10-02 14:52:00.979', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (251, '2020-10-02 14:52:24.413', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (252, '2020-10-02 14:54:48.917', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (253, '2020-10-02 15:09:49.871', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (254, '2020-10-02 15:22:28.263', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (255, '2020-10-02 15:23:41.134', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (256, '2020-10-02 15:25:22.684', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (257, '2020-10-02 15:26:42.238', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (258, '2020-10-02 15:28:49.536', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (259, '2020-10-02 16:59:31.017', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (260, '2020-10-02 16:59:51.883', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (261, '2020-10-02 17:02:18.976', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '200');
INSERT INTO public.log_data VALUES (262, '2020-10-02 17:09:18.387', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/user', '420');
INSERT INTO public.log_data VALUES (263, '2020-10-04 11:50:22.149', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/adm/function/page', '200');
INSERT INTO public.log_data VALUES (264, '2020-10-04 11:50:38.127', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/adm/function/page', '200');
INSERT INTO public.log_data VALUES (265, '2020-10-04 11:50:44.576', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/adm/function/page', '200');
INSERT INTO public.log_data VALUES (266, '2020-10-04 11:50:50.761', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/adm/function/page', '200');
INSERT INTO public.log_data VALUES (267, '2020-10-04 11:50:58.075', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/adm/function/page', '200');
INSERT INTO public.log_data VALUES (268, '2020-10-04 11:51:05.877', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/adm/function/page', '200');
INSERT INTO public.log_data VALUES (269, '2020-10-04 12:11:40.155', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/administration/adm/function', '200');
INSERT INTO public.log_data VALUES (270, '2020-10-04 12:12:16.124', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/nomenclature/adm/profile', '200');
INSERT INTO public.log_data VALUES (271, '2020-10-04 12:16:58.44', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/adm/function/assignment/{idAdmProfile}', '420');
INSERT INTO public.log_data VALUES (272, '2020-10-04 12:20:19.77', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/adm/function/assignment/{idAdmProfile}', '420');
INSERT INTO public.log_data VALUES (273, '2020-10-04 12:21:42.522', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/adm/function/assignment/{idAdmProfile}', '420');
INSERT INTO public.log_data VALUES (274, '2020-10-04 12:23:05.97', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/adm/function/assignment/{idAdmProfile}', '200');
INSERT INTO public.log_data VALUES (275, '2020-10-04 12:23:42.295', '192.168.137.1', 'cdc1db73-d9a1-4f67-95eb-95457efbf512', 'GET', '/account/function', '200');
INSERT INTO public.log_data VALUES (276, '2020-10-05 15:29:51.276', '192.168.1.152', 'cdc1db73-d9a1-4f67-95eb-95457efbf512', 'GET', '/account/function', '200');
INSERT INTO public.log_data VALUES (277, '2020-10-13 19:41:53.984', '192.168.1.58', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '420');
INSERT INTO public.log_data VALUES (278, '2020-10-13 19:42:33.28', '192.168.1.58', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '420');
INSERT INTO public.log_data VALUES (279, '2020-10-13 19:42:56.309', '192.168.1.58', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '200');
INSERT INTO public.log_data VALUES (280, '2020-10-13 19:43:17.581', '192.168.1.58', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '200');
INSERT INTO public.log_data VALUES (281, '2020-10-13 19:43:30.772', '192.168.1.58', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '200');
INSERT INTO public.log_data VALUES (282, '2020-10-13 19:44:02.029', '192.168.1.58', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '200');
INSERT INTO public.log_data VALUES (283, '2020-10-13 19:44:05.214', '192.168.1.58', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '200');
INSERT INTO public.log_data VALUES (284, '2020-10-13 19:44:41.366', '192.168.1.58', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '200');
INSERT INTO public.log_data VALUES (285, '2020-10-13 20:04:53.231', '192.168.1.58', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/account/whoiam', '200');
INSERT INTO public.log_data VALUES (286, '2020-10-13 20:10:52.512', '192.168.1.58', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/account/whoiam', '200');
INSERT INTO public.log_data VALUES (287, '2020-10-13 20:33:13.184', '192.168.1.58', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '420');
INSERT INTO public.log_data VALUES (288, '2020-10-13 20:33:43.727', '192.168.1.58', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '420');
INSERT INTO public.log_data VALUES (289, '2020-10-13 20:33:52.962', '192.168.1.58', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '420');
INSERT INTO public.log_data VALUES (290, '2020-10-13 20:34:34.119', '192.168.1.58', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '420');
INSERT INTO public.log_data VALUES (291, '2020-10-13 20:35:21.466', '192.168.1.58', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '420');
INSERT INTO public.log_data VALUES (292, '2020-10-13 20:36:07.946', '192.168.1.58', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '200');
INSERT INTO public.log_data VALUES (293, '2020-10-13 20:40:22.341', '192.168.1.58', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '200');
INSERT INTO public.log_data VALUES (294, '2020-10-13 20:44:41.152', '192.168.1.58', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '200');
INSERT INTO public.log_data VALUES (295, '2020-10-13 20:45:18.668', '192.168.1.58', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '200');
INSERT INTO public.log_data VALUES (296, '2020-10-13 20:57:05.873', '192.168.1.58', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '420');
INSERT INTO public.log_data VALUES (297, '2020-10-13 21:07:25.093', '192.168.1.58', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '200');
INSERT INTO public.log_data VALUES (298, '2020-10-13 21:07:43.261', '192.168.1.58', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '200');
INSERT INTO public.log_data VALUES (299, '2020-10-13 21:08:32.301', '192.168.1.58', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '200');
INSERT INTO public.log_data VALUES (300, '2020-10-14 15:46:17.599', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/data', '200');
INSERT INTO public.log_data VALUES (301, '2020-10-14 15:46:36.881', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '420');
INSERT INTO public.log_data VALUES (302, '2020-10-14 15:58:41.214', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '420');
INSERT INTO public.log_data VALUES (303, '2020-10-14 16:46:38.544', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '420');
INSERT INTO public.log_data VALUES (304, '2020-10-14 16:48:44.032', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '420');
INSERT INTO public.log_data VALUES (305, '2020-10-14 16:49:49.523', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '420');
INSERT INTO public.log_data VALUES (306, '2020-10-14 16:49:56.077', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '420');
INSERT INTO public.log_data VALUES (307, '2020-10-14 16:50:46.942', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '420');
INSERT INTO public.log_data VALUES (308, '2020-10-14 16:51:03.912', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '420');
INSERT INTO public.log_data VALUES (309, '2020-10-14 16:53:22.291', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '420');
INSERT INTO public.log_data VALUES (310, '2020-10-14 16:53:36.327', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '420');
INSERT INTO public.log_data VALUES (311, '2020-10-14 16:54:50.535', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '420');
INSERT INTO public.log_data VALUES (312, '2020-10-14 16:55:00.621', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '420');
INSERT INTO public.log_data VALUES (313, '2020-10-16 16:17:20.825', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '200');
INSERT INTO public.log_data VALUES (314, '2020-10-16 16:18:10.652', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '200');
INSERT INTO public.log_data VALUES (315, '2020-10-16 17:03:05.102', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '200');
INSERT INTO public.log_data VALUES (316, '2020-10-16 17:03:09.067', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '200');
INSERT INTO public.log_data VALUES (317, '2020-10-16 17:07:22.116', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '200');
INSERT INTO public.log_data VALUES (318, '2020-10-16 17:07:29.985', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '200');
INSERT INTO public.log_data VALUES (319, '2020-10-16 17:07:38.036', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '200');
INSERT INTO public.log_data VALUES (320, '2020-10-16 17:07:42.031', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '200');
INSERT INTO public.log_data VALUES (321, '2020-10-16 17:22:21.404', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '200');
INSERT INTO public.log_data VALUES (322, '2020-10-16 17:22:25.748', '192.168.1.152', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '200');
INSERT INTO public.log_data VALUES (323, '2020-10-18 19:00:20.562', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '200');
INSERT INTO public.log_data VALUES (324, '2020-10-18 19:00:51.632', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'POST', '/administration/log/access', '200');
INSERT INTO public.log_data VALUES (325, '2020-10-19 10:45:19.465', '192.168.137.1', '76e23153-5e21-43cd-921e-2364ebad8694', 'GET', '/account/whoiam', '200');


--
-- TOC entry 2222 (class 0 OID 108017)
-- Dependencies: 188
-- Data for Name: personnel; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.personnel VALUES ('51ab35f9-4d73-4308-a1f0-df4be7561c42', 'Yassine', 'Fourati', NULL, '2020-09-17 14:08:27.987', NULL);
INSERT INTO public.personnel VALUES ('9e1a8a78-6f03-4210-b29b-259a86004802', 'Ahmed', 'Berred', NULL, '2020-09-18 10:50:02.15', NULL);
INSERT INTO public.personnel VALUES ('8f2937ca-7f60-428e-8a33-ef609445017f', 'omar', 'fourati', '55333888', '2020-09-23 10:05:26.681', NULL);


--
-- TOC entry 2239 (class 0 OID 0)
-- Dependencies: 193
-- Name: seq_adm_function; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_adm_function', 30, false);


--
-- TOC entry 2240 (class 0 OID 0)
-- Dependencies: 201
-- Name: seq_adm_user_historique; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_adm_user_historique', 1, false);


--
-- TOC entry 2241 (class 0 OID 0)
-- Dependencies: 194
-- Name: seq_function_profile; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_function_profile', 3, true);


--
-- TOC entry 2242 (class 0 OID 0)
-- Dependencies: 195
-- Name: seq_log_access; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_log_access', 92, true);


--
-- TOC entry 2243 (class 0 OID 0)
-- Dependencies: 196
-- Name: seq_log_data; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_log_data', 325, true);


--
-- TOC entry 2072 (class 2606 OID 116105)
-- Name: adm_function adm_function_code_un; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.adm_function
    ADD CONSTRAINT adm_function_code_un UNIQUE (code);


--
-- TOC entry 2074 (class 2606 OID 108008)
-- Name: adm_function adm_function_id_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.adm_function
    ADD CONSTRAINT adm_function_id_pk PRIMARY KEY (id);


--
-- TOC entry 2076 (class 2606 OID 112712)
-- Name: adm_profile adm_profile_code_un; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.adm_profile
    ADD CONSTRAINT adm_profile_code_un UNIQUE (code);


--
-- TOC entry 2078 (class 2606 OID 108014)
-- Name: adm_profile adm_profile_id_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.adm_profile
    ADD CONSTRAINT adm_profile_id_pk PRIMARY KEY (id);


--
-- TOC entry 2092 (class 2606 OID 116080)
-- Name: adm_user_historique adm_user_historique_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.adm_user_historique
    ADD CONSTRAINT adm_user_historique_pkey PRIMARY KEY (id);


--
-- TOC entry 2082 (class 2606 OID 108035)
-- Name: adm_user adm_user_id_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.adm_user
    ADD CONSTRAINT adm_user_id_pk PRIMARY KEY (id);


--
-- TOC entry 2084 (class 2606 OID 108037)
-- Name: adm_user adm_user_login_un; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.adm_user
    ADD CONSTRAINT adm_user_login_un UNIQUE (login);


--
-- TOC entry 2086 (class 2606 OID 108052)
-- Name: function_profile function_profile_id_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.function_profile
    ADD CONSTRAINT function_profile_id_pk PRIMARY KEY (id);


--
-- TOC entry 2088 (class 2606 OID 108067)
-- Name: log_access log_access_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.log_access
    ADD CONSTRAINT log_access_pkey PRIMARY KEY (id);


--
-- TOC entry 2090 (class 2606 OID 108075)
-- Name: log_data log_data_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.log_data
    ADD CONSTRAINT log_data_pkey PRIMARY KEY (id);


--
-- TOC entry 2080 (class 2606 OID 108025)
-- Name: personnel personnel_id_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personnel
    ADD CONSTRAINT personnel_id_pk PRIMARY KEY (id);


--
-- TOC entry 2093 (class 2606 OID 116099)
-- Name: adm_function adm_function_id_parent_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.adm_function
    ADD CONSTRAINT adm_function_id_parent_fk FOREIGN KEY (id_parent) REFERENCES public.adm_function(id);


--
-- TOC entry 2095 (class 2606 OID 112706)
-- Name: adm_user adm_user_adm_profile_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.adm_user
    ADD CONSTRAINT adm_user_adm_profile_fk FOREIGN KEY (id_adm_profile) REFERENCES public.adm_profile(id);


--
-- TOC entry 2094 (class 2606 OID 108043)
-- Name: adm_user adm_user_personnel_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.adm_user
    ADD CONSTRAINT adm_user_personnel_fk FOREIGN KEY (id_personnel) REFERENCES public.personnel(id);


--
-- TOC entry 2096 (class 2606 OID 108053)
-- Name: function_profile function_profile_adm_function_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.function_profile
    ADD CONSTRAINT function_profile_adm_function_fk FOREIGN KEY (id_adm_function) REFERENCES public.adm_function(id);


--
-- TOC entry 2097 (class 2606 OID 108058)
-- Name: function_profile function_profile_adm_profile_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.function_profile
    ADD CONSTRAINT function_profile_adm_profile_fk FOREIGN KEY (id_adm_profile) REFERENCES public.adm_profile(id);


--
-- TOC entry 2098 (class 2606 OID 116054)
-- Name: log_access log_access_adm_user_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.log_access
    ADD CONSTRAINT log_access_adm_user_fk FOREIGN KEY (id_adm_user) REFERENCES public.adm_user(id);


--
-- TOC entry 2099 (class 2606 OID 112725)
-- Name: log_data log_data_adm_user_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.log_data
    ADD CONSTRAINT log_data_adm_user_fk FOREIGN KEY (id_adm_user) REFERENCES public.adm_user(id);


-- Completed on 2020-11-01 20:57:25

--
-- PostgreSQL database dump complete
--

